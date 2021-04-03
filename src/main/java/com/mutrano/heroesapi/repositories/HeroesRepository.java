package com.mutrano.heroesapi.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.mutrano.heroesapi.domain.Hero;

import reactor.core.publisher.Mono;

@Repository
public interface HeroesRepository extends ReactiveMongoRepository<Hero, String>{
	Mono<Hero> findByName(String name);
}
