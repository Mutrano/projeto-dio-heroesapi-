package com.mutrano.heroesapi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Hero {
	@Id	
	private Integer id;
	
	private String name;
	private String universe;
	private Integer appearances;

	public Hero() {
		
	}
	public Hero(Integer id, String name, String universe, Integer appearances) {
		this.id = id;
		this.name = name;
		this.universe = universe;
		this.appearances = appearances;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getAppearances() {
		return appearances;
	}

	public void setAppearances(Integer appearances) {
		this.appearances = appearances;
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
		Hero other = (Hero) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}