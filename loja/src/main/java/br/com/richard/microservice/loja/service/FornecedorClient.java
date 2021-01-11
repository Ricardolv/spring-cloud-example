package br.com.richard.microservice.loja.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.richard.microservice.loja.dto.InfoFornecedorResponse;
import br.com.richard.microservice.loja.dto.ItemDaCompraDTO;
import br.com.richard.microservice.loja.dto.PedidoResponse;

@FeignClient("fornecedor")
public interface FornecedorClient {
	
	@RequestMapping("/info/{estado}")
	InfoFornecedorResponse getInfoPorEstado(@PathVariable String estado);

	@RequestMapping(path = "/pedido",  method = RequestMethod.POST)
	PedidoResponse realizaPedidos(List<ItemDaCompraDTO> itens);

}
