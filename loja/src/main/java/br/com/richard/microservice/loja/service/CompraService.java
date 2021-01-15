package br.com.richard.microservice.loja.service;

import java.time.LocalDate;
import java.util.Objects;

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
import br.com.richard.microservice.loja.model.CompraState;
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
		
		
		Compra compraSalva = new Compra();
		compraSalva.setState(CompraState.RECEBIDO);
		compraSalva.setEnderecoDestino(compraDto.getEndereco().toString());
		compraRepository.save(compraSalva);
		
		compraDto.setCompraId(compraSalva.getId());
		
		String estado = compraDto.getEndereco().getEstado();		
		InfoFornecedorResponse infoPorEstado = fornecedorClient.getInfoPorEstado(estado);		
		PedidoResponse pedido = fornecedorClient.realizaPedidos(compraDto.getItens());
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraSalva.setState(CompraState.PEDIDO_REALIZADO);
		compraRepository.save(compraSalva);
				
		EntregaDTO entregaDto = new EntregaDTO();
		entregaDto.setPedidoId(pedido.getId());
		entregaDto.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));
		entregaDto.setEnderecoOrigem(infoPorEstado.getEndereco());
		entregaDto.setEnderecoDestino(compraDto.getEndereco().toString());
		VoucherDTO voucherDto = trasnportadorClient.reservaEntrega(entregaDto);
		compraSalva.setDataParaEntrega(voucherDto.getPrevisaoParaEntrega());
		compraSalva.setVoucher(voucherDto.getNumero());	
		compraSalva.setState(CompraState.RESERVA_ENTREGA_REALIZADA);
		
		LOG.info("compra registrada {}", compraSalva.getPedidoId());
		return compraRepository.save(compraSalva);
	}
	
	public Compra realizaCompraFallback(CompraDTO compraDto) {
		
		if (Objects.nonNull(compraDto.getCompraId())) {
			return compraRepository.findById(compraDto.getCompraId()).get();
		}
		
		Compra compra = new Compra();
		compra.setEnderecoDestino(compraDto.getEndereco().toString());
		return compra;
	}

}
