package com.prototypo.mscliente.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.base.Optional;
import com.prototypo.mscliente.dto.ClienteDTO;
import com.prototypo.mscliente.entity.Cliente;
import com.prototypo.mscliente.service.ClienteService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("clientes")
@Slf4j // acesso ao Log
public class ClienteController {

	@Autowired
	private ClienteService service;
	
	@PostMapping("")
	public ResponseEntity<ClienteDTO> save(@RequestBody ClienteDTO clienteDTO){
		
		Cliente cliente = clienteDTO.toModel();
		service.save(cliente);
		URI headerLacation = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.query("cpf={cpf}") // parâmetros que ira ser passado via url "parametro cpf"
				.buildAndExpand(cliente.getCpf()) // passando o valor que sera criado no parâmetro
				.toUri();
		return ResponseEntity.created(headerLacation).build();
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<?> dadosCliente(@RequestParam("cpf") String cpf){
		Optional<Cliente> cliente = service.getByCpf(cpf);
		
		if(!cliente.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cliente.get());
	}
	
	@GetMapping
	public String status() {
		log.info("Obtendo status");
		return "ok";
	}

}
