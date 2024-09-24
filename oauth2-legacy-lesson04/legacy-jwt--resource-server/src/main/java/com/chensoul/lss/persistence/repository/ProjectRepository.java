package com.chensoul.lss.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;

import com.chensoul.lss.persistence.model.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long>, CrudRepository<Project, Long> {

}
