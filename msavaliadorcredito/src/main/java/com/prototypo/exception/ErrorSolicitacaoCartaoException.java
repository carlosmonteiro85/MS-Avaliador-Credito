package com.prototypo.exception;

public class ErrorSolicitacaoCartaoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ErrorSolicitacaoCartaoException(String mensagem) {
		super(mensagem);
	}
}
