package com.pedidos.exceptions;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable {
	
	private static final long serialVersionUID = -2427852140358057016L;
	
	private Date data;
	private String descricaoException;
	private String detalhes;
	
	public ExceptionResponse(Date data, String descricaoException, String detalhes) {
		super();
		this.data = data;
		this.descricaoException = descricaoException;
		this.detalhes = detalhes;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricaoException() {
		return descricaoException;
	}

	public void setDescricaoException(String descricaoException) {
		this.descricaoException = descricaoException;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
}
