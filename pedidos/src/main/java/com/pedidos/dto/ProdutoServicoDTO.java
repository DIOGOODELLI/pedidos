package com.pedidos.dto;

import java.io.Serializable;
import java.util.UUID;

import com.pedidos.models.enums.TipoProdutoEnum;

public class ProdutoServicoDTO implements Serializable{

	private static final long serialVersionUID = -2160470972662493657L;
	
	private UUID id;
	private String descricao;
	private TipoProdutoEnum tipo;
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
