package br.com.richard.microservice.loja.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.richard.microservice.loja.dto.CompraDTO;
import br.com.richard.microservice.loja.dto.EntregaDTO;
import br.com.richard.microservice.loja.dto.InfoFornecedorResponse;
import br.com.richard.microservice.loja.dto.PedidoResponse;
import br.com.richard.microservice.loja.dto.VoucherDTO;
import br.com.richard.microservice.loja.model.Compra;
import br.com.richard.microservice.loja.repositories.CompraRepository;

@Service
public class CompraService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
	private final FornecedorClient fornecedorClient;
	private final TrasnportadorClient trasnportadorClient;
	private final CompraRepository compraRepository;
	
	public CompraService(FornecedorClient fornecedorClient, CompraRepository compraRepository, TrasnportadorClient trasnportadorClient) {
		this.fornecedorClient = fornecedorClient;
		this.trasnportadorClient = trasnportadorClient;
		this.compraRepository = compraRepository;
	}	
	
	@HystrixCommand(threadPoolKey = "findByIdThreadPool")
	public Compra findById(Long id) {
		return compraRepository.findById(id).orElse(null);
	}

	@HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDTO compraDto) {
		
		String estado = compraDto.getEndereco().getEstado();
		
		LOG.info("buscando informacoes do fornecedor {}", estado);
		InfoFornecedorResponse infoPorEstado = fornecedorClient.getInfoPorEstado(compraDto.getEndereco().getEstado());		
		
		LOG.info("realizando um pedido");
		PedidoResponse pedido = fornecedorClient.realizaPedidos(compraDto.getItens());
		
		EntregaDTO entregaDto = new EntregaDTO();
		entregaDto.setPedidoId(pedido.getId());
		entregaDto.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));
		entregaDto.setEnderecoOrigem(infoPorEstado.getEndereco());
		entregaDto.setEnderecoDestino(compraDto.getEndereco().toString());
		VoucherDTO voucherDto = trasnportadorClient.reservaEntrega(entregaDto);
		
		
		Compra compra = new Compra();
		compra.setPedidoId(pedido.getId());
		compra.setEnderecoDestino(compraDto.getEndereco().toString());
		compra.setTempoDePreparo(pedido.getTempoDePreparo());
		compra.setDataParaEntrega(voucherDto.getPrevisaoParaEntrega());
		compra.setVoucher(voucherDto.getNumero());
		Compra compraSalva = compraRepository.save(compra);
		
		LOG.info("compra registrada {}", compraSalva.getPedidoId());
		return compraSalva;
	}
	
	public Compra realizaCompraFallback(CompraDTO compraDto) {
		Compra compra = new Compra();
		compra.setEnderecoDestino(compraDto.getEndereco().toString());
		return compra;
	}

}
