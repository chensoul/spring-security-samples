package com.chensoul.lss.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;

import com.chensoul.lss.persistence.model.Project;

public interface IProjectRepository extends PagingAndSortingRepository<Project, Long>, CrudRepository<Project, Long> {

}
