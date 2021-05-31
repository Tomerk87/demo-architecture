package com.group.architecture.globe.repository;

import com.group.architecture.globe.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
