package com.chensoul.lss.web.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDto {
  private Long id;
  private String name;
  private LocalDate dateCreated;
}
