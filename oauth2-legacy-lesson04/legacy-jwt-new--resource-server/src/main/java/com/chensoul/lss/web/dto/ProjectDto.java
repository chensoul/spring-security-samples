package com.chensoul.lss.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProjectDto {
	private Long id;
	private String name;
	private LocalDate dateCreated;
}
