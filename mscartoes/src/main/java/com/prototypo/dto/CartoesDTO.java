package com.prototypo.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.prototypo.enums.BandeiraCartaoEnum;
import com.prototypo.model.Cartao;

import lombok.Data;

@Data
public class CartoesDTO {

	@NotBlank(message = "Nome do Cartão não pode estar em branco")
	private String nome;
	
	@NotBlank(message = "Bandeira não pode estar em branco")
	private BandeiraCartaoEnum bandeiraCartao;
	
	@NotBlank(message = "Renda não pode estar em branco")
	@NotNull(message = "Renda é obrigatório")
	private BigDecimal renda;
	
	@NotBlank(message = "limite não pode estar em branco")
	@NotNull(message = "Limite é obrigatório")
	private BigDecimal limiteBasico;

	public Cartao toModel() {
		return new Cartao(nome, bandeiraCartao, renda, limiteBasico);
	}
}
