package com.mutrano.heroesapi.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mutrano.heroesapi.builders.HeroDTOBuilder;
import com.mutrano.heroesapi.domain.Hero;
import com.mutrano.heroesapi.dto.HeroDTO;
import com.mutrano.heroesapi.repositories.HeroesRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
@SpringBootTest
public class HeroesServiceTest {
	@Autowired
	HeroesService heroesService;

	
	
	
	@MockBean
	HeroesRepository heroesRepository;

	@Test
	void whenRegisteredNameIsInformedAHeroShouldBeReturned() {
		//given
		HeroDTO expectedheroDTO = HeroDTOBuilder.build();
		Hero expectedHero = HeroDTOBuilder.fromDTO(expectedheroDTO);
		Mono<Hero> expectedHeroMono=Mono.just(expectedHero);
		//when
		when(heroesRepository.findByName(expectedheroDTO.getName())).thenReturn(expectedHeroMono);
		//then
		StepVerifier.create(heroesService.findByName(expectedheroDTO.getName()))
			.consumeNextWith( item->{
				assertThat(item.getId()).isEqualTo(expectedheroDTO.getId());
				assertThat(item.getName()).isEqualTo(expectedheroDTO.getName());
				assertThat(item.getUniverse()).isEqualTo(expectedheroDTO.getUniverse());
				assertThat(item.getAppearance()).isEqualTo(expectedheroDTO.getAppearance());
			})
			.verifyComplete();
	}
	@Test
	void whenUnregisteredNameIsInformedAnEmptyMonoShouldBeReturned() {
		//given
		HeroDTO expectedheroDTO = HeroDTOBuilder.build();
		Hero expectedHero = HeroDTOBuilder.fromDTO(expectedheroDTO);
		Mono<Hero> expectedHeroMono=Mono.just(expectedHero);
		//when
		when(heroesRepository.findByName(expectedheroDTO.getName())).thenReturn( Mono.empty());
		//then
		StepVerifier.create(heroesService.findByName(expectedheroDTO.getName()))
		.expectComplete()
		.verify();
	}
	@Test
	void whenValidHeroIsInformedAHeroShouldBeCreated(){
		//given
		HeroDTO informedHeroDTO = HeroDTOBuilder.build();
		informedHeroDTO.setId(null);
		Hero informedHero = HeroDTOBuilder.fromDTO(informedHeroDTO);
		HeroDTO expectedHeroDTO = HeroDTOBuilder.build();
		Hero expectedHero = HeroDTOBuilder.fromDTO(expectedHeroDTO);
		Mono<Hero> expectedHeroMono=Mono.just(expectedHero);
		//when
		when(heroesRepository.save(informedHero)).thenReturn(expectedHeroMono);
		when(heroesRepository.findByName(expectedHeroDTO.getName())).thenReturn(expectedHeroMono);
		//then
		StepVerifier.create(heroesService.insert(informedHeroDTO))
		.consumeNextWith(item->{
			assertThat(item.getId()).isEqualTo(expectedHero.getId());
			assertThat(item.getName()).isEqualTo(expectedHero.getName());
			assertThat(item.getUniverse()).isEqualTo(expectedHero.getUniverse());
			assertThat(item.getAppearance()).isEqualTo(expectedHero.getAppearances());
		}).verifyComplete();
	}
	@Test
	void whenRegisteredIdIsInformedTheHeroShouldBeDeletedAndTrueReturned() {
		//given
		HeroDTO informedHeroDTO = HeroDTOBuilder.build();
		Hero informedHero = HeroDTOBuilder.fromDTO(informedHeroDTO);
		//when
		when(heroesRepository.delete(informedHero)).thenReturn(Mono.empty());
		when(heroesRepository.findById(informedHeroDTO.getId())).thenReturn(Mono.just(informedHero));
		
		StepVerifier.create(heroesService.delete(informedHeroDTO.getId()))
			.consumeNextWith(item->{
				assertThat(item).isTrue();
			})
			.expectComplete()
			.verify();
	}
	@Test
	void whenUnregisteredIdIsInformedThenFalseShouldBeReturned() {
		//given
		HeroDTO informedHeroDTO = HeroDTOBuilder.build();
		Hero informedHero = HeroDTOBuilder.fromDTO(informedHeroDTO);
		//when
		when(heroesRepository.delete(informedHero)).thenReturn(Mono.empty());
		when(heroesRepository.findById(informedHeroDTO.getId())).thenReturn(Mono.empty());
		
		StepVerifier.create(heroesService.delete(informedHeroDTO.getId()))
			.consumeNextWith(item->{
				assertThat(item).isFalse();
			})
			.expectComplete()
			.verify();
	}
	@Test
	void whenFindAllIsCalledAListOfAllHeroesIsReturned() {
		//given
		HeroDTO expectedDto = HeroDTOBuilder.build();
		Hero expectedHero = HeroDTOBuilder.fromDTO(expectedDto);
		List<Hero> expectedHeroList = Collections.singletonList(expectedHero);
		//when
		when(heroesRepository.findAll()).thenReturn(Flux.fromIterable(expectedHeroList));
		//then
		
		StepVerifier.create(heroesService.findAll())
			.consumeNextWith(item -> {
				assertThat(item.getName()).isEqualTo(expectedHero.getName());
			} )
			.verifyComplete();
	}
}
