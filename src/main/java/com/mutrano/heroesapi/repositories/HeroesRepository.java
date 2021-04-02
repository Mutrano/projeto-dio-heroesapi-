package com.mutrano.heroesapi.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.mutrano.heroesapi.domain.Hero;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Repository
@Tag(name="Heroes")
public interface HeroesRepository extends ReactiveMongoRepository<Hero, String>{
	Mono<Hero> findByName(String name);
}
