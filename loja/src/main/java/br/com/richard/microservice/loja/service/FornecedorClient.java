package br.com.richard.microservice.loja.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.richard.microservice.loja.dto.InfoFornecedorResponse;

@FeignClient("fornecedor")
public interface FornecedorClient {
	
	@RequestMapping("/info/{estado}")
	InfoFornecedorResponse getInfoPorEstado(@PathVariable String estado);

}
