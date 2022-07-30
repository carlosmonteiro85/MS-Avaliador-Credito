package com.prototypo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prototypo.model.ClienteCartao;
import com.prototypo.repository.ClienteCartaoRepository;

@Service
public class ClienteCartaoService{

	private ClienteCartaoRepository repository;

	public ClienteCartaoService(@Autowired ClienteCartaoRepository repository) {
		this.repository = repository;
	}
	
	public List<ClienteCartao> getCartoesPorCpf(String cpf) {
		return repository.findByCpf(cpf);
	} 
	
}
