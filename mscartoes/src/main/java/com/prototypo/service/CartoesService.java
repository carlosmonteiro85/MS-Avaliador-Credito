package com.prototypo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prototypo.model.Cartao;
import com.prototypo.repository.CartoesRepository;

@Service
public class CartoesService{

	private CartoesRepository repository;

	public CartoesService(@Autowired CartoesRepository repository) {
		this.repository = repository;
	}
	
	public Cartao save(Cartao cartao) {
		return repository.save(cartao);
	} 
	
	public List<Cartao> getCartoesRendaMenorIgual(Long renda){
		return repository.findByRendaLessThanEqual(BigDecimal.valueOf(renda));
	}
	
}
