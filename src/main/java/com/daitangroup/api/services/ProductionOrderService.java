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

    public Optional<ProductionOrder> findProductionOrderById(long id) {
        return productionOrderRepository.findById(id);
    }

    public Iterable<ProductionOrder> findAllProductionOrders() {
        return productionOrderRepository.findAll();
    }

    public ProductionOrder findProductionOrderByUserId(long id) {

        Optional<Person> personResult = personRepository.findById(id);

        if(personResult.isPresent()) {
            Optional<ProductionOrder> productionOrderResult = productionOrderRepository.findByPerson(personResult.get());
            if(productionOrderResult.isPresent()) {
                return productionOrderResult.get();
            }
        }

        return null;
    }

    public Optional<ProductionOrder> findProductionOrderByVehicleId(long id) {

        Optional<Vehicle> vehicleResult = vehicleRepository.findById(id);

        if(vehicleResult.isPresent()){
            return productionOrderRepository.findByVehicle(vehicleResult.get());
        }

        return Optional.empty();
    }

    public ProductionOrder createProductionOrder(long personid, long vehicleid, ProductionOrder productionOrder) {

        Optional<Person> personResult = personRepository.findById(personid);
        Optional<Vehicle> vehicleResult = vehicleRepository.findById(vehicleid);

        if(personResult.isPresent() && vehicleResult.isPresent()) {
            productionOrder.setVehicle(vehicleResult.get());
            productionOrder.setPerson(personResult.get());

            return productionOrderRepository.save(productionOrder);
        }

        return null;
    }

    public ProductionOrder updateProductionOrder(Optional<Long> personid, Optional<Long> vehicleid, ProductionOrder productionOrder) {

        Optional<ProductionOrder> productionOrderResult = productionOrderRepository.findById(productionOrder.getId());

        if(productionOrderResult.isPresent()) {

            if(personid.isPresent()) {

                Optional<Person> person = personRepository.findById(personid.get());

                if(person.isPresent()){
                    productionOrder.setPerson(person.get());
                }
            }

            if(vehicleid.isPresent()) {

                Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleid.get());

                if(vehicle.isPresent()) {
                    productionOrder.setVehicle(vehicle.get());
                }
            }

            if(productionOrder.getDescription() == null) {
                productionOrder.setDescription(productionOrderResult.get().getDescription());
            }

            if(productionOrder.getCompletionForecast() == null) {
                productionOrder.setCompletionForecast(productionOrderResult.get().getCompletionForecast());
            }

            if(productionOrder.getFinished() == null) {
                productionOrder.setFinished(productionOrderResult.get().getFinished());
            }

            return productionOrderRepository.save(productionOrder);
        }

        return null;
    }

    public ProductionOrder deleteProductionOrderById(long id) {
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
