package com.pedidos.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.pedidos.models.enums.TipoProdutoEnum;

@Entity
@Table(name = "tb_produto_servico")
public class ProdutoServico implements Serializable{

	private static final long serialVersionUID = 6857086926293099294L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	
	@NotNull
	@Column
	private String descricao;
	
	@NotNull
	@Column
	@Enumerated(EnumType.STRING)
	private TipoProdutoEnum tipo;
	
    @ColumnDefault("true")
	@Column()
	private Boolean enabled;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public TipoProdutoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoProdutoEnum tipo) {
		this.tipo = tipo;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
