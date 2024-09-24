package com.chensoul.lss.persistence.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Entity
@Data
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private LocalDate dateCreated = LocalDate.now();

	protected Project() {
	}

	public Project(String name, LocalDate dateCreated) {
		this.name = name;
		this.dateCreated = dateCreated;
	}
}
