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
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @Autowired
    private PersonRepository personRepository;

    public Optional<Vehicle> findByVehicleId(long id) {
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
