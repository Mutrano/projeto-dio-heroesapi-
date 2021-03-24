package com.mutrano.heroesapi.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.mutrano.heroesapi.domain.Hero;

@Repository
public interface HeroRepository extends ReactiveCrudRepository<Hero, Integer>{

}
