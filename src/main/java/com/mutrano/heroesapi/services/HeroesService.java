package com.mutrano.heroesapi.services;


import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.mutrano.heroesapi.domain.Hero;
import com.mutrano.heroesapi.dto.HeroDTO;
import com.mutrano.heroesapi.repositories.HeroesRepository;
import com.mutrano.heroesapi.resources.exceptions.StandardError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HeroesService {
	HeroesRepository heroesRepository;
	public HeroesService(HeroesRepository heroesRepository) {
		this.heroesRepository = heroesRepository;
	}

	public Mono<HeroDTO> insert( HeroDTO hero){
		return heroesRepository.save(new Hero(hero.getId(),hero.getName(),hero.getUniverse(),hero.getAppearance()   )   )
				.flatMap(item->
				Mono.just(new HeroDTO(item.getId(),item.getName(),item.getUniverse(),item.getAppearances())))
				.flatMap(item -> findByName(item.getName()));
	}
	

	public  Mono<HeroDTO> findByName(String name){
		return heroesRepository.findByName(name)
				.flatMap(item->
				Mono.just(new HeroDTO(item.getId(),item.getName(),item.getUniverse(),item.getAppearances())));
	}
	public Flux<Hero> findAll(){
		return heroesRepository.findAll();
	}

}
