package com.cts.ora.report.fetch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.ora.report.domain.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
