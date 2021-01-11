package br.com.richard.microservice.loja.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.richard.microservice.loja.dto.CompraDTO;
import br.com.richard.microservice.loja.dto.InfoFornecedorResponse;
import br.com.richard.microservice.loja.dto.PedidoResponse;
import br.com.richard.microservice.loja.model.Compra;
import br.com.richard.microservice.loja.repositories.CompraRepository;

@Service
public class CompraService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
	private final FornecedorClient fornecedorClient;
	private final CompraRepository compraRepository;
	
	public CompraService(FornecedorClient fornecedorClient, CompraRepository compraRepository) {
		this.fornecedorClient = fornecedorClient;
		this.compraRepository = compraRepository;
	}	
	
	@HystrixCommand
	public Compra findById(Long id) {
		return compraRepository.findById(id).orElse(null);
	}

	@HystrixCommand(fallbackMethod = "realizaCompraFallback")
	public Compra realizaCompra(CompraDTO compraDto) {
		
		String estado = compraDto.getEndereco().getEstado();
		
		LOG.info("buscando informacoes do fornecedor {}", estado);
		InfoFornecedorResponse infoPorEstado = fornecedorClient.getInfoPorEstado(compraDto.getEndereco().getEstado());		
		LOG.info("infoPorEstado endereco {}", infoPorEstado.getEndereco());
		
		LOG.info("realizando um pedido");
		PedidoResponse pedido = fornecedorClient.realizaPedidos(compraDto.getItens());
		
		Compra compra = new Compra(pedido.getId(), pedido.getTempoDePreparo(), compraDto.getEndereco().toString());
		Compra compraSalva = compraRepository.save(compra);
		LOG.info("compra registrada {}", compraSalva.getPedidoId());
		
		return compraSalva;
	}
	
	public Compra realizaCompraFallback(CompraDTO compra) {
		return new Compra(null, null, compra.getEndereco().toString());
	}

}
