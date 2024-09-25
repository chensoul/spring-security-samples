package com.chensoul.lss.persistence.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;


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
