package com.group.architecture.globe.repository;


import com.group.architecture.globe.model.entity.Continent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContinentRepository extends JpaRepository<Continent, Long> {
}
