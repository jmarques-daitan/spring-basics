package com.daitangroup.api.controller;

import com.daitangroup.api.model.Vehicle;
import com.daitangroup.api.services.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private GarageService garageService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable> findAllVehicles() {

        Iterable<Vehicle> vehiclesResult = garageService.findAllVehicles();

        return new ResponseEntity<>(vehiclesResult, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable Long id) {

        Optional<Vehicle> vehicle = garageService.findVehicleById(id);

        if(vehicle.isPresent()) {
            return new ResponseEntity<>(vehicle.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/plate/{plate}")
    public ResponseEntity<Vehicle> findVehicleByPlate(@PathVariable String plate) {

        Optional<Vehicle> vehicle = garageService.findVehicleByPlate(plate);

        if(vehicle.isPresent()) {
            return new ResponseEntity<>(vehicle.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/person/{id}")
    public ResponseEntity<Iterable> findVehiclesByPersonId(@PathVariable long id) {

        Iterable<Vehicle> vehiclesResult = garageService.findVehiclesByPersonId(id);

        if (vehiclesResult != null && vehiclesResult.iterator().hasNext()) {
            return new ResponseEntity<>(vehiclesResult, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {

        Vehicle vehicleCreated = garageService.createVehicle(vehicle);

        if(vehicleCreated != null) {
            return new ResponseEntity<>(vehicleCreated, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update/{id}")
    public ResponseEntity<Vehicle> updatePerson(@PathVariable long id, @RequestBody Vehicle vehicle) {

        vehicle.setId(id);
        Vehicle vehicleChanged = garageService.updateVehicle(vehicle);

        if(vehicleChanged != null) {
            return new ResponseEntity<>(vehicleChanged, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Vehicle> deleteVehicle(@PathVariable long id) {

        Vehicle vehicleDeleted = garageService.deleteVehicleById(id);

        if(vehicleDeleted != null) {
            return new ResponseEntity<>(vehicleDeleted, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete-all")
    public ResponseEntity<String> deleteAllVehicles() {

        garageService.deleteAllVehicles();

        return new ResponseEntity<String>("All vehicles deleted", HttpStatus.OK);
    }

}
