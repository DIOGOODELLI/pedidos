package com.pedidos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.BAD_REQUEST)
public class ExceptionsService extends RuntimeException {
	private static final long serialVersionUID = -690996874072101140L;
	public ExceptionsService (String descricaoErro) {
		super(descricaoErro);
	}
	

}
 