package br.com.richard.microservice.loja.model;

public class Compra {
	
	private Long pedidoId;
	private Integer tempoDePreparo;
	private String enderecoDestino;
	
	public Compra(Long pedidoId, Integer tempoDePreparo, String enderecoDestino) {
		this.pedidoId = pedidoId;
		this.tempoDePreparo = tempoDePreparo;
		this.enderecoDestino = enderecoDestino;
	}

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
	public void setEndereco(String endereco) {
		this.enderecoDestino = endereco;
	}

}
