package com.mutrano.heroesapi.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HeroDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String id;
	@NotBlank(message="Name must be not blank")
	String name;
	@NotBlank(message="Universe must be not blank")  
	String universe;
	@NotNull(message="Appearance must be not null")  
	Integer appearance;
	public HeroDTO() {}
	public HeroDTO(String id, @NotBlank(message = "Name must be not blank") String name, @NotBlank(message = "Universe must be not blank") String universe,
			@NotBlank(message = "Appearance must be not null") Integer appearance) {
		this.id = id;
		this.name = name;
		this.universe = universe;
		this.appearance = appearance;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUniverse() {
		return universe;
	}
	public void setUniverse(String universe) {
		this.universe = universe;
	}
	public Integer getAppearance() {
		return appearance;
	}
	public void setAppearance(Integer appearance) {
		this.appearance = appearance;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HeroDTO other = (HeroDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "HeroDTO [id=" + id + ", name=" + name + ", universe=" + universe + ", appearance=" + appearance + "]";
	}

	
	
	
}