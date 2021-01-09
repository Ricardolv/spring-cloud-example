package br.com.richard.microservice.loja.service;

import org.springframework.stereotype.Service;

import br.com.richard.microservice.loja.dto.CompraDTO;
import br.com.richard.microservice.loja.dto.InfoFornecedorResponse;

@Service
public class CompraService {
	
	private final FornecedorClient fornecedorClient;
	
	public CompraService(FornecedorClient fornecedorClient) {
		this.fornecedorClient = fornecedorClient;
	}


	public void realizaCompra(CompraDTO compra) {
		
		InfoFornecedorResponse infoPorEstado = fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());
		
		System.out.println(infoPorEstado.getEndereco());
		
	}

}
