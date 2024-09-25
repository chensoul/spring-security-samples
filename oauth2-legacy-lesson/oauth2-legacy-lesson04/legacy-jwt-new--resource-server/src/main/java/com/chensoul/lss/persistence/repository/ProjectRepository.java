package com.chensoul.lss.persistence.repository;

import com.chensoul.lss.persistence.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long>, CrudRepository<Project, Long> {

}
