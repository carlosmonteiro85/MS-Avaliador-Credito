package com.prototypo.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.prototypo.enums.BandeiraCartaoEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cartao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Enumerated(EnumType.STRING)
	private BandeiraCartaoEnum bandeiraCartao;
	private BigDecimal renda;
	private BigDecimal limiteBasico;
	
	public Cartao(String nome, BandeiraCartaoEnum bandeiraCartao, BigDecimal renda, BigDecimal limiteBasico) {
		this.nome = nome;
		this.bandeiraCartao = bandeiraCartao;
		this.renda = renda;
		this.limiteBasico = limiteBasico;
	}
}
