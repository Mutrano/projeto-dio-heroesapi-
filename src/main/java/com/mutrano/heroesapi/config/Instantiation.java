package com.mutrano.heroesapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mutrano.heroesapi.domain.Hero;
import com.mutrano.heroesapi.dto.HeroDTO;
import com.mutrano.heroesapi.repositories.HeroesRepository;

import reactor.core.publisher.Mono;

@Configuration
@Profile(value = "test")
public class Instantiation implements CommandLineRunner {
	@Autowired
	HeroesRepository heroesRepository;
	
	@Override
	public void run(String... args) throws Exception {
/*		Hero hero = new Hero("", "socorro", "marvel", 3);
		HeroDTO dto = new HeroDTO(hero.getId(),hero.getName(),hero.getUniverse(),hero.getAppearances());
		System.out.println("tamo aqui");
		heroesRepository.deleteAll().block();
		
		
		
	Mono<Hero>saved = heroesRepository.save(hero)
		 .doOnNext(
				item -> System.out.println(item+"olha="+item.getId())
				)
		 .doOnSuccess(item->System.out.println("cara pelo amor de deus "+item));
	saved.subscribe();
	
		 heroesRepository.findByName(hero.getName())
		 .doOnNext(
					item -> System.out.println(item)
					)
		 .subscribe();*/
		
	}

}
