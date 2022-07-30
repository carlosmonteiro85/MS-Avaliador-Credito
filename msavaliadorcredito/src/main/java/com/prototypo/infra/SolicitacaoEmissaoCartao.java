package com.prototypo.infra;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prototypo.model.DadosSolicitacaoEmissaoCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartao {
	
	private final RabbitTemplate rt;
	private final Queue filaEmissaoCartoes; // configurada pela classe MqConfig
	
	public void solicitarCartao(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
		String json = convertInJson(dados);
		// responsavel em converter e enviar os dados
		rt.convertAndSend(filaEmissaoCartoes.getName(), json); 
	}
	/*
	 * método responsavel em converter o objeto que sera enviado para fila em json
	 * */
	private String convertInJson(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dados);
	}

}
