package com.chensoul.lss.web.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProjectDto {
	Long id;

	String name;

	LocalDate dateCreated;


}
