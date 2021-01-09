package br.com.richard.microservice.fornecedor.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.richard.microservice.fornecedor.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{

}
