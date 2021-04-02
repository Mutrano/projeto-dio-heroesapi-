package com.mutrano.heroesapi.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mutrano.heroesapi.dto.HeroDTO;
import com.mutrano.heroesapi.services.HeroesService;
import com.mutrano.heroesapi.services.exceptions.ResourceNotFoundException;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/heroes")
public class HeroesResource {
	HeroesService heroesService;

	public HeroesResource(HeroesService heroesService) {
		this.heroesService = heroesService;
	}

	@GetMapping("/{name}")
	public Mono<ResponseEntity<HeroDTO>> findByName(@PathVariable String name, ServerHttpRequest request) {
		return heroesService.findByName(name).flatMap((item) -> Mono.just(ResponseEntity.ok(item)))
				.switchIfEmpty(Mono.error(new ResourceNotFoundException(name, request.getPath().toString())));
	}

	@PostMapping
	public Mono<ResponseEntity<HeroDTO>> insert(@RequestBody @Valid HeroDTO dto, ServerHttpRequest request) {
		return heroesService.insert(dto).flatMap(
				item -> Mono.just(ResponseEntity.created(URI.create(request.getPath() + item.getId())).body(item)));
	}
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
		return heroesService.delete(id)
				.flatMap(item->item
						? Mono.just(ResponseEntity.ok().build()) 
						:Mono.just(ResponseEntity.noContent().build())  
						);
		
	}



}
