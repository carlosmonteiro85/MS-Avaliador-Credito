package com.prototypo.exception;

import lombok.Getter;

public class ErroComunicacaoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	// status da exceção
	@Getter
	private Integer status;
	
	// construtor com mensagem e status da exceção 
	public ErroComunicacaoException(String m, Integer status) {
		super();
		this.status = status;
	}

}
