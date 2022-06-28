package com.pedidos.dto;

import java.io.Serializable;
import java.util.UUID;

public class PedidoItemDTO implements Serializable{

	private static final long serialVersionUID = -6146701136625032111L;
	
	private PedidoDTO pedido;
	private ProdutoServicoDTO produtoServico;
	private Double valor_unitario;
	private Double quantidade;
	private Double valor_total;
	private UUID id;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public PedidoDTO getPedido() {
		return pedido;
	}
	
	public void setPedido(PedidoDTO pedido) {
		this.pedido = pedido;
	}
	
	public ProdutoServicoDTO getProdutoServico() {
		return produtoServico;
	}
	
	public void setProdutoServico(ProdutoServicoDTO produtoServico) {
		this.produtoServico = produtoServico;
	}
	
	public Double getValor_unitario() {
		return valor_unitario;
	}
	
	public void setValor_unitario(Double valor_unitario) {
		this.valor_unitario = valor_unitario;
	}
	
	public Double getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	
	public Double getValor_total() {
		return valor_total;
	}
	
	public void setValor_total(Double valor_total) {
		this.valor_total = valor_total;
	}
}
