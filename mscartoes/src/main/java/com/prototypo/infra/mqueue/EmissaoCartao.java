package com.prototypo.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prototypo.model.Cartao;
import com.prototypo.model.ClienteCartao;
import com.prototypo.model.DadosSolicitacaoEmissaoCartao;
import com.prototypo.repository.CartoesRepository;
import com.prototypo.repository.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//habilitar o @Componente para que o spring gerencie
@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartao {
	
	private final CartoesRepository cartaRrepository;
	private final ClienteCartaoRepository clienteRepository;

	/*
	 * método que ira escutar a fila configurada 
	 * */
	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void receberSolicitacaoEmissao(@Payload String payload) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
			Cartao cartao = cartaRrepository.findById(dados.getIdCartao()).orElseThrow();
			ClienteCartao clienteCartao = new ClienteCartao();
			clienteCartao.setCartao(cartao);
			clienteCartao.setCpf(dados.getCpf());
			clienteCartao.setLimite(dados.getLimite());
			
			clienteRepository.save(clienteCartao);
			
		} catch (Exception e) {
			log.error("Error ao emitir yma solicitação de cartão: {}", e.getMessage());
		} 
	}
}
