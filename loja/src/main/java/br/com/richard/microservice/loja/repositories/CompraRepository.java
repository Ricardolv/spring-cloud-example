package br.com.richard.microservice.loja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.richard.microservice.loja.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long>{

}
