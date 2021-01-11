package br.com.richard.microservice.loja.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.richard.microservice.loja.dto.EntregaDTO;
import br.com.richard.microservice.loja.dto.VoucherDTO;

@FeignClient("transportador")
public interface TrasnportadorClient {

	@RequestMapping(path = "/entrega", method = RequestMethod.POST)
	VoucherDTO reservaEntrega(EntregaDTO entregaDto);

}
