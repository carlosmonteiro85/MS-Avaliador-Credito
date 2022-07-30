package com.prototypo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prototypo.exception.DadosClientesNotFoundException;
import com.prototypo.exception.ErroComunicacaoException;
import com.prototypo.exception.ErrorSolicitacaoCartaoException;
import com.prototypo.model.DadosAvaliacao;
import com.prototypo.model.DadosSolicitacaoEmissaoCartao;
import com.prototypo.model.ProtocoloAvaliacaoCartao;
import com.prototypo.model.RetornoAvaliacaoCliente;
import com.prototypo.model.SituacaoCliente;
import com.prototypo.service.AvaliadorClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor 
public class AvaliadorCreditoController {
	
	private final AvaliadorClienteService avaliadorClienteService;
	
	@GetMapping
	public String status() {
		return "ok";
	}

	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity consultaSituacaoCliente( @RequestParam("cpf") String cpf){
		try {
			SituacaoCliente	situacaoCliente = avaliadorClienteService.obterSituacaoCliente(cpf);
			return ResponseEntity.ok(situacaoCliente);			
		} catch (DadosClientesNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados ) {
		try {
			RetornoAvaliacaoCliente retornoAvaliacao = avaliadorClienteService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
			return ResponseEntity.ok(retornoAvaliacao);			
		} catch (DadosClientesNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
	
	@PostMapping("solicitacao-cartao")
	public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
		try {
			ProtocoloAvaliacaoCartao protocolo = avaliadorClienteService.solicitarEmissaoCartao(dados);
			return ResponseEntity.ok(protocolo);
		} catch (ErrorSolicitacaoCartaoException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
