package com.mutrano.heroesapi.services;


import org.springframework.stereotype.Service;

import com.mutrano.heroesapi.repositories.HeroRepository;

@Service
public class HeroesService {
	HeroRepository heroRepository;
	

	public HeroesService(HeroRepository heroRepository) {
		this.heroRepository = heroRepository;
	}

}
