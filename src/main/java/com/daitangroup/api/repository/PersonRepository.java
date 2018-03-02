package com.daitangroup.api.repository;

import com.daitangroup.api.model.Person;
import org.springframework.data.repository.CrudRepository;


import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findByCpf(String cpf);

}
