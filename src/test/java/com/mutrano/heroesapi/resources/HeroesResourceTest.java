package com.mutrano.heroesapi.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mutrano.heroesapi.builders.HeroDTOBuilder;
import com.mutrano.heroesapi.domain.Hero;
import com.mutrano.heroesapi.dto.HeroDTO;
import com.mutrano.heroesapi.repositories.HeroesRepository;
import com.mutrano.heroesapi.resources.exceptions.ResourceExceptionHandler;
import com.mutrano.heroesapi.resources.exceptions.ValidationError;
import com.mutrano.heroesapi.services.HeroesService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@ContextConfiguration(classes = { HeroesResource.class ,ResourceExceptionHandler.class })
public class HeroesResourceTest {

	@Autowired
	private ApplicationContext context;

	@MockBean
	private HeroesService heroesService;

	@MockBean
	private HeroesRepository heroesRepository;

	private WebTestClient webTestClient;

	@BeforeEach
	public void setUp() {
		webTestClient = WebTestClient.bindToApplicationContext(context).build();
	}

	@Test
	void whenRegisteredNameIsInformedAHeroShouldBeReturned() {
		//given
		HeroDTO heroDTO = HeroDTOBuilder.build();
		Mono<HeroDTO> heroMono = Mono.just(heroDTO);
		
		//when
		when(heroesService.findByName("super morango")).thenReturn(heroMono);
		
		//then
		webTestClient.get()
			.uri("/heroes/" + heroDTO.getName())
			.exchange()
			.expectStatus().isOk()
			.expectBody(HeroDTO.class).value(response -> {
				assertThat(response.getName()).isEqualTo(heroDTO.getName());
				assertThat(response.getUniverse()).isEqualTo(heroDTO.getUniverse());
				assertThat(response.getAppearance()).isEqualTo(heroDTO.getAppearance());
				});
	}

	@Test
	void whenUnregisteredNameIsInformedAnStandardErrorShouldBeReturned() {
		//given
		HeroDTO heroDTO = HeroDTOBuilder.build();
		Mono<HeroDTO> heroMono = Mono.just(heroDTO);
		//when
		when(heroesService.findByName("super morango")).thenReturn(Mono.empty());
		//then
		webTestClient.get()
		.uri("/heroes/"+heroDTO.getName())
		.exchange()
		.expectStatus().isNotFound();
	}
	@Test
	void whenValidHeroIsInformedAHeroShouldBeCreated() {
		//given
		HeroDTO heroDTO = HeroDTOBuilder.build();
		heroDTO.setId(null);
		HeroDTO expectedHeroDTO = HeroDTOBuilder.build();
		Mono<HeroDTO> heroMono = Mono.just(heroDTO);
		Mono<HeroDTO> expectedHeroMono= Mono.just(expectedHeroDTO);
		//when
		when(heroesService.insert(heroDTO)).thenReturn(expectedHeroMono);
		
		//then
		webTestClient.post()
		.uri("/heroes/")
		.bodyValue(heroDTO)
		.exchange()
		.expectStatus().isCreated()
		.expectBody(HeroDTO.class)
		.consumeWith(item->{
			var response = item.getResponseBody();
			assertThat(response.getId()).isEqualTo(expectedHeroDTO.getId());
			assertThat(response.getName()).isEqualTo(expectedHeroDTO.getName());
			assertThat(response.getUniverse()).isEqualTo(expectedHeroDTO.getUniverse());
			assertThat(response.getAppearance()).isEqualTo(expectedHeroDTO.getAppearance());
		});
	}

	@Test
	void whenInvalidHeroIsInformedAnValidationErrorShuldBeReturned() {
		//given
		HeroDTO heroDTO = HeroDTOBuilder.build();
		heroDTO.setAppearance(null);
		Mono<HeroDTO> heroMono= Mono.just(heroDTO);
		//then
		webTestClient.post()
		.uri("/heroes/")
		.bodyValue(heroDTO)
		.exchange()
		.expectStatus().isBadRequest()
		.expectBody(ValidationError.class)
		.consumeWith(item->{	
			var response = item.getResponseBody();
			assertThat(response.getErrors().size()).isEqualTo(1);
			assertThat(response.getErrors().get(0).getFieldName()).isEqualTo("appearance");
		});
	}
	@Test
	void whenRegisteredIdIsInformedAHeroShouldBeDeletedAndOkStatusReturned() {
		//given
		HeroDTO heroDTO = HeroDTOBuilder.build();
		//when
		when(heroesService.delete(heroDTO.getId())).thenReturn(Mono.just(true));
		//then
		webTestClient.delete()
		.uri("/heroes/"+heroDTO.getId())
		.exchange()
		.expectStatus().isOk()
		.expectBody().isEmpty();
		
		
	}
	@Test
	void whenUnregisteredIdIsInformedAHeroShouldBeDeletedAndNoContentStatusReturned() {
		//given
		HeroDTO heroDTO = HeroDTOBuilder.build();
		//when
		when(heroesService.delete(heroDTO.getId())).thenReturn(Mono.just(false));
		//then
		webTestClient.delete()
		.uri("/heroes/"+heroDTO.getId())
		.exchange()
		.expectStatus().isNoContent()
		.expectBody().isEmpty();
	}
	
	@Test
	void whenFindAllEndpointIsCalledAHeroListShouldBeReturned() {
		//given
		HeroDTO expectedHeroDTO = HeroDTOBuilder.build();
		Hero expectedHero = HeroDTOBuilder.fromDTO(expectedHeroDTO);
		List<HeroDTO> expectedHeroDTOList = Collections.singletonList(expectedHeroDTO);
		//when
		when(heroesService.findAll()).thenReturn(Flux.fromIterable(expectedHeroDTOList));
		//then
		webTestClient.get()
		.uri("/heroes/")
		.exchange()
		.expectStatus().isOk()
		.expectBody(List.class);
	}
	
}
