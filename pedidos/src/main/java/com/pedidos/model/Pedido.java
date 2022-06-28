package com.pedidos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "tb_pedido")
public class Pedido implements Serializable{
	
	private static final long serialVersionUID = 1612723279589496780L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	
	@NotNull
	@Column()
	@ColumnDefault("0.00")
	private Double valor_servicos;
	
	@NotNull
	@Column()
	@ColumnDefault("0.00")
	private Double valor_produtos;
	
	@NotNull
	@Column()
	@ColumnDefault("0.00")
	private Double valor_total;
	
	@NotNull
	@Column
	private String situacao; 
	
	@NotNull
	@Column()
	@Min(0)
	@Max(90)
	@ColumnDefault("0")
	private int percentualDesconto;
	
	@NotNull
	@Column
	private long numero_pedido;
	
	@NotNull
	@Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date data_pedido;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public Date getData_pedido() {
		return data_pedido;
	}

	public void setData_pedido(Date data_pedido) {
		this.data_pedido = data_pedido;
	}
}
