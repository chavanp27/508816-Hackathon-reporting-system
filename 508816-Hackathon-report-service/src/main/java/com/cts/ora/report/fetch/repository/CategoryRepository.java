package com.cts.ora.report.fetch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.ora.report.domain.model.EventCategory;

public interface CategoryRepository extends JpaRepository<EventCategory, Integer> {

}
