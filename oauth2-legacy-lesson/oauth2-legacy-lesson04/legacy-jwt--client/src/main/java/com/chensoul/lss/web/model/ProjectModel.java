package com.chensoul.lss.web.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ProjectModel {

  private Long id;
  private String name;
  private LocalDate dateCreated;

  public ProjectModel() {
  }

  public ProjectModel(Long id, String name, LocalDate dateCreated) {
    super();
    this.id = id;
    this.name = name;
    this.dateCreated = dateCreated;
  }
}
