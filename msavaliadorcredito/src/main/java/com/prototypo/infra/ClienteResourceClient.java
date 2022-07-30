package com.prototypo.infra;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prototypo.model.DadosClientes;

/*
 * @Value será p nome do microserviço que ira ser consulrado
 * @path sera o end-point do controler a ser utilizado
 * 	Comunicação sincrona entre micro serviços
 * */
@FeignClient(value = "msclientes", path = "/clientes")
public interface ClienteResourceClient {
	
	// chamada que sera usada no microserviço que ira ser invocado
	@GetMapping(params = "cpf")
	ResponseEntity<DadosClientes> dadosCliente(@RequestParam("cpf") String cpf);
}
