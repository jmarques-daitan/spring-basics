package com.daitangroup.api.repository;

import com.daitangroup.api.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
