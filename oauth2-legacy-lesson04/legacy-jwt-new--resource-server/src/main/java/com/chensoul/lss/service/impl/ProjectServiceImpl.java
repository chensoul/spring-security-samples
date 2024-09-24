package com.chensoul.lss.service.impl;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chensoul.lss.persistence.model.Project;
import com.chensoul.lss.persistence.repository.ProjectRepository;
import com.chensoul.lss.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
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
