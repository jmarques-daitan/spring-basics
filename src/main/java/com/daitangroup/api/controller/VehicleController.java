package com.daitangroup.api.controller;


import com.daitangroup.api.model.ProductionOrder;
import com.daitangroup.api.model.Vehicle;
import com.daitangroup.api.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable Long id) {

        Optional<Vehicle> vehicle = vehicleService.findById(id);

        if(vehicle.isPresent()) {
            return new ResponseEntity<>(vehicle.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/plate/{plate}")
    public ResponseEntity<Vehicle> findVehicleByPlate(@PathVariable String plate) {

        Optional<Vehicle> vehicle = vehicleService.findByPlate(plate);

        if(vehicle.isPresent()) {
            return new ResponseEntity<>(vehicle.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable> findAllVehicles() {

        Iterable<Vehicle> vehicles = vehicleService.findAll();

        return new ResponseEntity<Iterable>(vehicles, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/po/{id}")
    public ResponseEntity<ProductionOrder> findPoSByVehicleId(@PathVariable Long id) {

        Optional<ProductionOrder> productionOrder = vehicleService.findPoByVehicleId(id);

        if(productionOrder.isPresent()) {
            return new ResponseEntity<>(productionOrder.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {

        Vehicle vehicleCreated = vehicleService.createVehicle(vehicle);

        if(vehicleCreated != null) {
            return new ResponseEntity<>(vehicleCreated, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/change/{id}")
    public ResponseEntity<Vehicle> changePerson(@PathVariable long id, @RequestBody Vehicle vehicle) {

        Vehicle vehicleChanged = vehicleService.changeVehicle(id, vehicle);

        if(vehicleChanged != null) {
            return new ResponseEntity<>(vehicleChanged, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Vehicle> deleteVehicle(@PathVariable long id) {

        Vehicle vehicleDeleted = vehicleService.deleteVehicle(id);

        if(vehicleDeleted != null) {
            return new ResponseEntity<>(vehicleDeleted, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteall")
    public ResponseEntity<String> deleteAllVehicles() {

        vehicleService.deleteAllVehicles();

        return new ResponseEntity<String>("All vehicles deleted",HttpStatus.OK);
    }

}
