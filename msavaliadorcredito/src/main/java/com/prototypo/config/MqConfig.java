package com.prototypo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Classe responsavel em criar e configurar a fila injetando
 * pelo spring
 * */
@Configuration
public class MqConfig {
	
	// valor do application.yml injetado pelo spring
	@Value("${mq.queues.emissao-cartoes}")
	private String emissaoCartaoFila;
	
	@Bean
	public Queue filaEmissaoCartao() {
		return new Queue(emissaoCartaoFila, true);
	}
}
