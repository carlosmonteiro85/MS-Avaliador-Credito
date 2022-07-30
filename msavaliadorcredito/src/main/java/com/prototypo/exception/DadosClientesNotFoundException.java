package com.prototypo.exception;

public class DadosClientesNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public DadosClientesNotFoundException() {
		super("Dados do cliente não encontrado para o CPF informado.");
	}
	
}
