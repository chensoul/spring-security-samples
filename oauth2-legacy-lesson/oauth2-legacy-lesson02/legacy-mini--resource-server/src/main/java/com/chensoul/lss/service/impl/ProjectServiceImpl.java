package com.chensoul.lss.service.impl;

import com.chensoul.lss.persistence.model.Project;
import com.chensoul.lss.persistence.repository.IProjectRepository;
import com.chensoul.lss.service.IProjectService;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements IProjectService {

  private IProjectRepository projectRepository;

  public ProjectServiceImpl(IProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public Optional<Project> findById(Long id) {
    return projectRepository.findById(id);
  }

  @Override
  public Project save(Project project) {
    if (Objects.isNull(project.getId())) {
      project.setDateCreated(LocalDate.now());
    }
    return projectRepository.save(project);
  }

  @Override
  public Iterable<Project> findAll() {
    return projectRepository.findAll();
  }
}
