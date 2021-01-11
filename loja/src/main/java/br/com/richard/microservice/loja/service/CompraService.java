package br.com.richard.microservice.loja.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.richard.microservice.loja.dto.CompraDTO;
import br.com.richard.microservice.loja.dto.InfoFornecedorResponse;
import br.com.richard.microservice.loja.dto.PedidoResponse;
import br.com.richard.microservice.loja.model.Compra;

@Service
public class CompraService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
	private final FornecedorClient fornecedorClient;
	
	public CompraService(FornecedorClient fornecedorClient) {
		this.fornecedorClient = fornecedorClient;
	}


	public Compra realizaCompra(CompraDTO compra) {
		
		String estado = compra.getEndereco().getEstado();
		
		LOG.info("buscando informacoes do fornecedor {}", estado);
		InfoFornecedorResponse infoPorEstado = fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());		
		
		LOG.info("realizando um pedido");
		PedidoResponse pedido = fornecedorClient.realizaPedidos(compra.getItens());
		
		
		return new Compra(pedido.getId(), pedido.getTempoDePreparo(), infoPorEstado.getEndereco());
	}

}
