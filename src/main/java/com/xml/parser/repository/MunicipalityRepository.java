package com.xml.parser.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.xml.parser.entity.Municipality;

@Repository
public interface MunicipalityRepository extends ListCrudRepository<Municipality, Long> {
}
