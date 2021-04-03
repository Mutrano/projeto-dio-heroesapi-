package com.mutrano.heroesapi.services;


import org.springframework.stereotype.Service;

import com.mutrano.heroesapi.domain.Hero;
import com.mutrano.heroesapi.dto.HeroDTO;
import com.mutrano.heroesapi.repositories.HeroesRepository;

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
	public Mono<Boolean> delete(String id){

		Mono<Hero> heroMono = heroesRepository.findById(id);
		
		heroMono
			.flatMap(hero-> heroesRepository.deleteById(id))
			.subscribe();
		
		return heroMono
			.flatMap(item-> Mono.just(true))
			.switchIfEmpty(Mono.just(false));
	}
	
	public Flux<HeroDTO> findAll(){
		return heroesRepository.findAll().
				flatMap(item-> Mono.just(new HeroDTO(item.getId(),item.getName(),item.getUniverse(),item.getAppearances())) );
	}

}