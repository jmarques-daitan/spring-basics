package com.daitangroup.api.services;

import com.daitangroup.api.model.Person;
import com.daitangroup.api.model.ProductionOrder;
import com.daitangroup.api.model.Vehicle;
import com.daitangroup.api.repository.PersonRepository;
import com.daitangroup.api.repository.ProductionOrderRepository;
import com.daitangroup.api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductionOrderService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public Optional<ProductionOrder> findById(long id) {
        return productionOrderRepository.findById(id);
    }

    public Iterable<ProductionOrder> findAll() {
        return productionOrderRepository.findAll();
    }

    public ProductionOrder createProductionOrder(long personid, long vehicleid, ProductionOrder productionOrder) {

        Optional<Person> person = personRepository.findById(personid);
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleid);

        if(person.isPresent() && vehicle.isPresent()) {
            productionOrder.setVehicle(vehicle.get());
            productionOrder.setPerson(person.get());

            return productionOrderRepository.save(productionOrder);
        }

        return null;
    }

    public ProductionOrder changeProductionOrder(long productionId, Optional<Long> personid, Optional<Long> vehicleid, ProductionOrder productionOrder) {

        Optional<ProductionOrder> productionOrderToChange = productionOrderRepository.findById(productionId);

        if(productionOrderToChange.isPresent()) {
            ProductionOrder productionOrderToSet = productionOrderToChange.get();

            if(personid.isPresent()) {

                Optional<Person> person = personRepository.findById(personid.get());

                if(person.isPresent()){
                    productionOrderToSet.setPerson(person.get());
                }
            }

            if(vehicleid.isPresent()) {

                Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleid.get());

                if(vehicle.isPresent()) {
                    productionOrderToSet.setVehicle(vehicle.get());
                }
            }

            if(productionOrder.getDescription() != null) {
                productionOrderToSet.setDescription(productionOrder.getDescription());
            }

            if(productionOrder.getCompletionForecast() != null) {
                productionOrderToSet.setCompletionForecast(productionOrder.getCompletionForecast());
            }

            if(productionOrder.getFinished() != null) {
                productionOrderToSet.setFinished(productionOrder.getFinished());
            }

            return productionOrderRepository.save(productionOrderToSet);
        }

        return null;
    }

    public ProductionOrder deleteProductionOrder(long id) {
        Optional<ProductionOrder> productionOrderToDelete = productionOrderRepository.findById(id);

        if(productionOrderToDelete.isPresent()) {
            productionOrderRepository.delete(productionOrderToDelete.get());
            return productionOrderToDelete.get();
        }
        return null;
    }

    public void deleteAllProductionOrders() {
        productionOrderRepository.deleteAll();
    }
}
