package com.chensoul.lss.service;

import java.util.Optional;

import com.chensoul.lss.persistence.model.Project;

public interface IProjectService {

    Optional<Project> findById(Long id);

    Project save(Project project);

    Iterable<Project> findAll();

}
