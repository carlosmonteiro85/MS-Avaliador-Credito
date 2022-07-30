package com.prototypo.mscliente.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.prototypo.mscliente.entity.Cliente;

import lombok.Data;

@Data
public class ClienteDTO {
	@NotBlank(message = "Cpf não pode estar em branco")
	@NotNull(message = "cpf é obrigatório")
	private String cpf;
	
	@NotBlank(message = "Nome não pode estar em branco")
	private String nome;
	
	@NotBlank(message = "Idade não pode estar em branco")
	@NotNull(message = "Idade é obrigatório")
	private Integer idade;

	public Cliente toModel() {
		return new Cliente(this.cpf, this.nome, this.idade);
	}
}
