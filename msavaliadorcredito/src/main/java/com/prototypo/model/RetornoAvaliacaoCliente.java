package com.prototypo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RetornoAvaliacaoCliente {
	private List<CartaoAprovado> cartoes;
}
