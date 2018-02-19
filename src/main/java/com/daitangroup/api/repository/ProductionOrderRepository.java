package com.daitangroup.api.repository;

import com.daitangroup.api.model.Person;
import com.daitangroup.api.model.ProductionOrder;
import com.daitangroup.api.model.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductionOrderRepository extends CrudRepository<ProductionOrder, Long> {

    Optional<ProductionOrder> findByPerson (Person person);

    Optional<ProductionOrder> findByVehicle (Vehicle vehicle);
}
