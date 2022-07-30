package com.prototypo.dto;

import java.math.BigDecimal;

import com.prototypo.enums.BandeiraCartaoEnum;
import com.prototypo.model.ClienteCartao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCartaoDTO {

	private String nome;
	private BandeiraCartaoEnum bandeira;
	private BigDecimal limiteLiberado;

	public static ClienteCartaoDTO toDTO(ClienteCartao cliente) {
		return new ClienteCartaoDTO(cliente.getCartao().getNome(), cliente.getCartao().getBandeiraCartao(),
				cliente.getLimite());
	}
}
