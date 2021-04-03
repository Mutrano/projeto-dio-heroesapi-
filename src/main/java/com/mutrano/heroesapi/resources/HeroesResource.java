package com.mutrano.heroesapi.resources;

import java.net.URI;

import javax.validation.Valid;

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
import com.mutrano.heroesapi.resources.exceptions.StandardError;
import com.mutrano.heroesapi.resources.exceptions.ValidationError;
import com.mutrano.heroesapi.services.HeroesService;
import com.mutrano.heroesapi.services.exceptions.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/heroes")
public class HeroesResource {
	HeroesService heroesService;

	public HeroesResource(HeroesService heroesService) {
		this.heroesService = heroesService;
	}
	  private static final org.slf4j.Logger log =
			    org.slf4j.LoggerFactory.getLogger(HeroesResource.class);

	
	@ApiResponses({@ApiResponse(responseCode = "200"),@ApiResponse(responseCode="404",content = @Content(schema = @Schema(implementation = StandardError.class)))})
	@GetMapping("/{name}")
	public Mono<ResponseEntity<HeroDTO>> findByName(@PathVariable String name, ServerHttpRequest request) {
		log.info("Requesting the hero wich name is "+name);
		return heroesService.findByName(name).flatMap((item) -> Mono.just(ResponseEntity.ok(item)))
				.switchIfEmpty(Mono.error(new ResourceNotFoundException(name, request.getPath().toString())));
	}
	@ApiResponses({@ApiResponse(responseCode = "201"),@ApiResponse(responseCode="400",content=@Content(schema=@Schema(implementation = ValidationError.class)))})
	@PostMapping
	public Mono<ResponseEntity<HeroDTO>> insert(@RequestBody @Valid HeroDTO dto, ServerHttpRequest request) {
		log.info("Creating a hero");
		return heroesService.insert(dto).flatMap(
				item -> Mono.just(ResponseEntity.created(URI.create(request.getPath() + item.getId())).body(item)));
	}
	@ApiResponses({@ApiResponse(responseCode = "200"),@ApiResponse(responseCode="204")})
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
		log.info("Deleting the hero with id "+id);
		return heroesService.delete(id)
				.flatMap(item->item
						? Mono.just(ResponseEntity.ok().build()) 
						:Mono.just(ResponseEntity.noContent().build())  
						);
	}
	
	@GetMapping()
	public Flux<HeroDTO> findAll(){
		log.info("Requesting a List with all heroes");
		return heroesService.findAll();
	}

}
