package com.prototypo.controll;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prototypo.dto.CartoesDTO;
import com.prototypo.dto.ClienteCartaoDTO;
import com.prototypo.model.Cartao;
import com.prototypo.model.ClienteCartao;
import com.prototypo.service.CartoesService;
import com.prototypo.service.ClienteCartaoService;

@RestController
@RequestMapping("cartoes")
public class CartoesController {

	@Autowired
	private CartoesService cartaoService;
	
	@Autowired
	private ClienteCartaoService clienteService;

	@GetMapping
	public String status() {
		return "ok";
	}

	@PostMapping
	public ResponseEntity<CartoesDTO> save(@RequestBody CartoesDTO request) {
		Cartao cartao = request.toModel();
		cartaoService.save(cartao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda ){
		List<Cartao> cartoesRendaMenorIgual = cartaoService.getCartoesRendaMenorIgual(renda);
		return ResponseEntity.ok(cartoesRendaMenorIgual);
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<List<ClienteCartaoDTO>> getCartoesByCliente(@RequestParam("cpf") String cpf){
		List<ClienteCartao> cartoesPorCpf = clienteService.getCartoesPorCpf(cpf);
		List<ClienteCartaoDTO> list = cartoesPorCpf.stream()
				.map(ClienteCartaoDTO::toDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(list);
	}
}
