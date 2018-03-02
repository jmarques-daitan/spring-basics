package com.daitangroup.api.services;

import com.daitangroup.api.exception.CpfAlreadyRegisteredException;
import com.daitangroup.api.model.Person;
import com.daitangroup.api.model.ProductionOrder;
import com.daitangroup.api.model.Vehicle;
import com.daitangroup.api.repository.PersonRepository;
import com.daitangroup.api.repository.ProductionOrderRepository;
import com.daitangroup.api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class GarageService {

    private PersonRepository personRepository;

    private ProductionOrderRepository productionOrderRepository;

    private VehicleRepository vehicleRepository;

    @Autowired
    public GarageService(PersonRepository personRepository,
                         ProductionOrderRepository productionOrderRepository,
                         VehicleRepository vehicleRepository) {
        this.personRepository = personRepository;
        this.productionOrderRepository = productionOrderRepository;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Person methods
     */

    public Optional<Person> findPersonById(long id) {
        return personRepository.findById(id);
    }

    public Iterable<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> findPersonByCpf(String cpf) {
        return personRepository.findByCpf(cpf);
    }

    public Person createPerson(Person person) throws CpfAlreadyRegisteredException {

        Optional<Person> personToCheck = personRepository.findByCpf(person.getCpf());

        if(personToCheck.isPresent()){
            throw new CpfAlreadyRegisteredException("This CPF is already in use");
        }

        return personRepository.save(person);
    }

    public Person addVehicleToPerson(long personId, long vehicleId) {

        Optional<Person> personResult = personRepository.findById(personId);
        Optional<Vehicle> vehicleResult = vehicleRepository.findById(vehicleId);

        if(personResult.isPresent() && vehicleResult.isPresent()) {
            Person personPresent = personResult.get();
            Vehicle vehiclePresent = vehicleResult.get();
            Set<Vehicle> vehicles = new HashSet<Vehicle>();

            vehiclePresent.setPerson(personPresent);

            vehicles.add(vehiclePresent);

            personPresent.setVehicles(vehicles);

            //personRepository.save(personPresent);
            vehicleRepository.save(vehiclePresent);
            return personPresent;
        }

        return null;
    }

    public Person updatePerson(Person person) {

        Optional<Person> personResult = personRepository.findById(person.getId());

        if(personResult.isPresent()) {

            if(person.getFirstName() == null) {
                person.setFirstName(personResult.get().getFirstName());
            }

            if(person.getLastName() == null) {
                person.setLastName(personResult.get().getLastName());
            }

            if(person.getCpf() == null) {
                person.setCpf(personResult.get().getCpf());
            }

            if(person.getPhone() == null) {
                person.setPhone(personResult.get().getPhone());
            }
            return personRepository.save(person);
        }
        return null;
    }

    public Person deletePerson(long id) {

        Optional<Person> personToDelete = personRepository.findById(id);

        if(personToDelete.isPresent()) {
            personRepository.delete(personToDelete.get());
            return personToDelete.get();
        }

        return null;
    }

    public void deleteAllPersons() {
        personRepository.deleteAll();
    }

    /**
     * ProductionOrder methods
     */

    public Optional<ProductionOrder> findProductionOrderById(long id) {
        return productionOrderRepository.findById(id);
    }

    public Iterable<ProductionOrder> findAllProductionOrders() {
        return productionOrderRepository.findAll();
    }

    public ProductionOrder findProductionOrderByPersonId(long id) {

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
            } else {
                productionOrder.setPerson(productionOrderResult.get().getPerson());
            }

            if(vehicleid.isPresent()) {

                Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleid.get());

                if(vehicle.isPresent()) {
                    productionOrder.setVehicle(vehicle.get());
                }

            } else {
                productionOrder.setVehicle(productionOrderResult.get().getVehicle());
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

    /**
     * Vehicle methods
     */

    public Optional<Vehicle> findVehicleById(long id) {
        return vehicleRepository.findById(id);
    }

    public Optional<Vehicle> findVehicleByPlate(String plate) {
        return vehicleRepository.findByPlate(plate);
    }

    public Iterable<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Iterable<Vehicle> findVehiclesByPersonId(long id) {

        Optional<Person> personResult = personRepository.findById(id);

        if(personResult.isPresent()) {
            return personResult.get().getVehicles();
        }

        return null;
    }

    public Vehicle createVehicle(Vehicle vehicle) {

        Optional<Vehicle> vehicleToCheckPlate = vehicleRepository.findByPlate(vehicle.getPlate());

        if(vehicleToCheckPlate.isPresent()){
            return null;
        }

        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Vehicle vehicle) {

        Optional<Vehicle> vehicleResult = vehicleRepository.findById(vehicle.getId());

        if(vehicleResult.isPresent()) {

            if(vehicle.getPlate() == null) {
                vehicle.setPlate(vehicleResult.get().getPlate());
            }

            if(vehicle.getModel() == null) {
                vehicle.setModel(vehicleResult.get().getModel());
            }

            if(vehicle.getColor() == null) {
                vehicle.setColor(vehicleResult.get().getColor());
            }
            return vehicleRepository.save(vehicle);
        }

        return null;
    }

    public Vehicle deleteVehicleById(long id) {

        Optional<Vehicle> vehicleToDelete = vehicleRepository.findById(id);

        if(vehicleToDelete.isPresent()) {
            vehicleRepository.delete(vehicleToDelete.get());
            return vehicleToDelete.get();
        }

        return null;
    }

    public void deleteAllVehicles() {
        vehicleRepository.deleteAll();
    }
}
