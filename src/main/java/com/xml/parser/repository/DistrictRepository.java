package com.xml.parser.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.xml.parser.entity.District;

@Repository
public interface DistrictRepository extends ListCrudRepository<District, Long> {
}
