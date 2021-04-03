package com.mutrano.heroesapi.builders;

import java.util.Collections;
import java.util.List;

import com.mutrano.heroesapi.domain.Hero;
import com.mutrano.heroesapi.dto.HeroDTO;

public class HeroDTOBuilder {
	public static HeroDTO build() {
		return new HeroDTO("123", "super morango", "dc", 0);
	}
	
	public static Hero fromDTO(HeroDTO dto) {
		return new Hero(dto.getId(), dto.getName(), dto.getUniverse(), dto.getAppearance());
	}
}
