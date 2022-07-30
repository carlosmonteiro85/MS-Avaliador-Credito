package com.prototypo.mscliente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.prototypo.mscliente.entity.Cliente;
import com.prototypo.mscliente.repository.ClienteRepository;

@Service
public class ClienteService {

	private final ClienteRepository repository;

	public ClienteService(@Autowired ClienteRepository repository) {
		this.repository = repository;
	}
	
	public Cliente save(Cliente cliente) {
		return repository.save(cliente);
	}

	public Optional<Cliente> getByCpf(String cpf){
		return repository.findByCpf(cpf);
	}
}
