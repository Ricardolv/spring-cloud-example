package br.com.richard.microservice.loja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.richard.microservice.loja.dto.CompraDTO;
import br.com.richard.microservice.loja.model.Compra;
import br.com.richard.microservice.loja.service.CompraService;

@RestController
@RequestMapping("/compra")
public class CompraController {
	
	@Autowired
	private CompraService compraService;

	@GetMapping(path = "/{id}")
	public ResponseEntity<Compra> findById(@PathVariable Long id) {
		Compra compra = compraService.findById(id);
		return ResponseEntity.ok(compra);
	}
	
	
	@PostMapping
	public ResponseEntity<Compra> realizaCompra(@RequestBody CompraDTO compra) {
		Compra compraRealizada = compraService.realizaCompra(compra);
		return ResponseEntity.status(HttpStatus.CREATED).body(compraRealizada);
	}
}
