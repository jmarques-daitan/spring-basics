package com.daitangroup.api.services;

import com.daitangroup.api.model.ProductionOrder;
import com.daitangroup.api.model.Vehicle;
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

    public Optional<Vehicle> findById(long id) {
        return vehicleRepository.findById(id);
    }

    public Optional<Vehicle> findByPlate(String plate) {
        return vehicleRepository.findByPlate(plate);
    }

    public Iterable<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Optional<ProductionOrder> findPoByVehicleId(long id) {

        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        if(vehicle.isPresent()){
            return productionOrderRepository.findByVehicle(vehicle.get());
        }

        return Optional.empty();
    }

    public Vehicle createVehicle(Vehicle vehicle) {

        Optional<Vehicle> vehicleToCheckPlate = vehicleRepository.findByPlate(vehicle.getPlate());

        if(vehicleToCheckPlate.isPresent()){
            return null;
        }

        return vehicleRepository.save(vehicle);
    }

    public Vehicle changeVehicle(long id, Vehicle vehicle) {

        Optional<Vehicle> vehicleToChange = vehicleRepository.findById(id);

        if(vehicleToChange.isPresent()) {
            Vehicle vehicleToSet = vehicleToChange.get();

            if(vehicle.getPlate() != null) {
                vehicleToSet.setPlate(vehicle.getPlate());
            }

            if(vehicle.getModel() != null) {
                vehicleToSet.setModel(vehicle.getModel());
            }

            if(vehicle.getColor() != null) {
                vehicleToSet.setColor(vehicle.getColor());
            }
            return vehicleRepository.save(vehicleToSet);
        }

        return null;
    }

    public Vehicle deleteVehicle(long id) {

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
