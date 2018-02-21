package com.daitangroup.api.controller;

import com.daitangroup.api.model.Person;
import com.daitangroup.api.services.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private GarageService garageService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Person> findPersonById(@PathVariable Long id) {

        Optional<Person> personResult = garageService.findPersonById(id);

        if (personResult.isPresent()) {
            return new ResponseEntity<>(personResult.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable> findAllPersons() {

        Iterable<Person> personsResult = garageService.findAllPersons();

        if (personsResult.iterator().hasNext()) {
            return new ResponseEntity<>(personsResult, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cpf/{cpf}")
    public ResponseEntity<Person> findPersonByCpf(@PathVariable String cpf) {

        Optional<Person> personResult = garageService.findPersonByCpf(cpf);

        if (personResult.isPresent()) {
            return new ResponseEntity<>(personResult.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {

        Person personSaved = garageService.createPerson(person);

        if(personSaved != null){
            return new ResponseEntity<>(person, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/associatevehicle")
    public ResponseEntity<Person> addVehicleToPerson(@RequestParam("personid") long personid, @RequestParam("vehicleid") long vehicleid) {

        Person person = garageService.addVehicleToPerson(personid, vehicleid);

        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/change/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable long id, @RequestBody Person person) {

        person.setId(id);
        Person personUpdated = garageService.updatePerson(person);

        if(person != null) {
            return new ResponseEntity<>(personUpdated, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable long id) {

        Person personDeleted = garageService.deletePerson(id);

        if(personDeleted != null){
            return new ResponseEntity<>(personDeleted, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteall")
    public ResponseEntity<String> deleteAllPersons() {

        garageService.deleteAllPersons();

        return new ResponseEntity<String>("All persons deleted",HttpStatus.OK);
    }
}
