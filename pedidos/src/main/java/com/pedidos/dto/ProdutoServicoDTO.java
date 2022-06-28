package com.pedidos.dto;

import java.io.Serializable;
import java.util.UUID;

public class ProdutoServicoDTO implements Serializable{

	private static final long serialVersionUID = -2160470972662493657L;
	
	private UUID id;
	private String descricao;
	private String tipo;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
