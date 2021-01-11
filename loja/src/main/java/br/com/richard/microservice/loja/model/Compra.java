package br.com.richard.microservice.loja.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Compra {
	
	@Id
	@Column(name = "id")
	private Long pedidoId;
	
	@Column(name = "tempo_preparo")
	private Integer tempoDePreparo;
	
	@Column(name = "endereco_destino")
	private String enderecoDestino;
	
	private Long voucher;
	
	@Column(name = "data_entrega")
	private LocalDate dataParaEntrega;
	
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public Integer getTempoDePreparo() {
		return tempoDePreparo;
	}
	public void setTempoDePreparo(Integer tempoDePreparo) {
		this.tempoDePreparo = tempoDePreparo;
	}
	public String getEnderecoDestino() {
		return enderecoDestino;
	}
	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}
	public Long getVoucher() {
		return voucher;
	}
	public void setVoucher(Long voucher) {
		this.voucher = voucher;
	}
	public LocalDate getDataParaEntrega() {
		return dataParaEntrega;
	}
	public void setDataParaEntrega(LocalDate dataParaEntrega) {
		this.dataParaEntrega = dataParaEntrega;
	}
	
	
}
