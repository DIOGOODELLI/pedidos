package com.pedidos.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class PedidoDTO implements Serializable{
	
	private static final long serialVersionUID = 5111607020629114980L;
	
	private Double valor_servicos;
	private Double valor_produtos;
	private Double valor_total;
	private String situacao; 
	private int percentualDesconto;
	private long numero_pedido;
	private UUID id;
	private Date data_pedido;
	
	public Double getValor_servicos() {
		return valor_servicos;
	}

	public void setValor_servicos(Double valor_servicos) {
		this.valor_servicos = valor_servicos;
	}

	public Double getValor_produtos() {
		return valor_produtos;
	}

	public void setValor_produtos(Double valor_produtos) {
		this.valor_produtos = valor_produtos;
	}

	public Double getValor_total() {
		return valor_total;
	}

	public void setValor_total(Double valor_total) {
		this.valor_total = valor_total;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(int percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public long getNumero_pedido() {
		return numero_pedido;
	}

	public void setNumero_pedido(long numero_pedido) {
		this.numero_pedido = numero_pedido;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getData_pedido() {
		return data_pedido;
	}

	public void setData_pedido(Date data_pedido) {
		this.data_pedido = data_pedido;
	}
}
