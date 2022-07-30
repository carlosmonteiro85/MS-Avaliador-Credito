package com.prototypo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // utilizado para a criação do objeto
public class SituacaoCliente {
	private DadosClientes cliente;
	private List<CartaoCliente> cartoes;
}
