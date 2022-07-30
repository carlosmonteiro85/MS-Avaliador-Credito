package com.prototypo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.prototypo.exception.DadosClientesNotFoundException;
import com.prototypo.exception.ErroComunicacaoException;
import com.prototypo.exception.ErrorSolicitacaoCartaoException;
import com.prototypo.infra.CartoesResolurceClient;
import com.prototypo.infra.ClienteResourceClient;
import com.prototypo.infra.SolicitacaoEmissaoCartao;
import com.prototypo.model.Cartao;
import com.prototypo.model.CartaoAprovado;
import com.prototypo.model.CartaoCliente;
import com.prototypo.model.DadosClientes;
import com.prototypo.model.DadosSolicitacaoEmissaoCartao;
import com.prototypo.model.ProtocoloAvaliacaoCartao;
import com.prototypo.model.RetornoAvaliacaoCliente;
import com.prototypo.model.SituacaoCliente;

import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // injete via contrutor
public class AvaliadorClienteService {

	private final ClienteResourceClient clientesClient;
	private final CartoesResolurceClient cartoesClient;
	private final SolicitacaoEmissaoCartao emissorCartaoPublish;
	
	public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClientesNotFoundException, ErroComunicacaoException {
		
		try {
			ResponseEntity<DadosClientes> dadosCliente = clientesClient.dadosCliente(cpf);
			ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);			
			return SituacaoCliente
					.builder()
					.cliente(dadosCliente.getBody())
					.cartoes(cartoesResponse.getBody())
					.build();
			
		} catch (FeignClientException e) {
			int status = e.status(); // status da exceção
			// Tratamento para caso o cliente não seja encontrado
			if(HttpStatus.NOT_FOUND.value() == status ) {
				// lanchando a exceção criada para capturar no controller
				throw new DadosClientesNotFoundException(); 
			}
			throw new ErroComunicacaoException(e.getMessage(), status);
		}
	}
	
	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
			throws DadosClientesNotFoundException, ErroComunicacaoException {

		try {
			ResponseEntity<DadosClientes> dadosCliente = clientesClient.dadosCliente(cpf);
			ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAte(renda);

			List<Cartao> cartoes = cartoesResponse.getBody();

			List<CartaoAprovado> cartoesAprovados = cartoes.stream().map(c -> {

				BigDecimal limiteBasico = c.getLimiteBasico();
				BigDecimal idadeCliente = BigDecimal.valueOf(dadosCliente.getBody().getIdade());
				BigDecimal total = idadeCliente.divide(BigDecimal.valueOf(10));

				BigDecimal liniteAprovado = total.multiply(limiteBasico);

				CartaoAprovado cartaoAprovado = new CartaoAprovado();

				cartaoAprovado.setCartao(c.getNome());
				cartaoAprovado.setBandeira(c.getBandeiraCartao());
				cartaoAprovado.setLimiteAprovado(liniteAprovado);

				return cartaoAprovado;
			}).collect(Collectors.toList());

			return RetornoAvaliacaoCliente.builder().cartoes(cartoesAprovados).build();

		} catch (FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClientesNotFoundException();
			}
			throw new ErroComunicacaoException(e.getMessage(), status);
		}
	}
	
	public ProtocoloAvaliacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dodos) {
		try {
			emissorCartaoPublish.solicitarCartao(dodos);
			String protocolo = UUID.randomUUID().toString();
			return new ProtocoloAvaliacaoCartao(protocolo);
		} catch (Exception e) {
			throw new ErrorSolicitacaoCartaoException(e.getMessage());
		}
	}

}
