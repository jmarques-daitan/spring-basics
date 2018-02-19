package com.daitangroup.api.controller;

import com.daitangroup.api.model.Person;
import com.daitangroup.api.model.ProductionOrder;
import com.daitangroup.api.model.Vehicle;
import com.daitangroup.api.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Person> findPersonById(@PathVariable Long id) {

        Optional<Person> person = personService.findById(id);

        if (person.isPresent()) {
            return new ResponseEntity<>(person.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Iterable> findAllPersons() {

        Iterable<Person> persons = personService.findAll();

        if (persons.iterator().hasNext()) {
            return new ResponseEntity<>(persons, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cpf/{cpf}")
    public ResponseEntity<Person> findByCpf(@PathVariable String cpf) {

        Optional<Person> person = personService.findByCpf(cpf);

        if (person.isPresent()) {
            return new ResponseEntity<>(person.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/po/{id}")
    public ResponseEntity<ProductionOrder> findPoByUserId(@PathVariable long id) {

        ProductionOrder productionOrder = personService.findPoByUserId(id);

        if (productionOrder != null) {
            return new ResponseEntity<>(productionOrder, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vehicles/{id}")
    public ResponseEntity<Iterable> findVehiclesByPersonId(@PathVariable long id) {

        Iterable<Vehicle> vehicles = personService.findVehiclesByPersonId(id);

        if (vehicles != null && vehicles.iterator().hasNext()) {
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {

        Person personSaved = personService.createPerson(person);

        if(personSaved != null){
            return new ResponseEntity<>(person, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/associatevehicle")
    public ResponseEntity<Person> associateVehicle(@RequestParam("personid") long personid, @RequestParam("vehicleid") long vehicleid) {

        Person person = personService.associateVehicle(personid, vehicleid);

        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/change/{id}")
    public ResponseEntity<Person> changePerson(@PathVariable long id, @RequestBody Person person) {

        Person personChanged = personService.changePerson(id, person);

        if(person != null) {
            return new ResponseEntity<>(personChanged, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable long id) {

        Person person = personService.deletePerson(id);

        if(person != null){
            return new ResponseEntity<>(person, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteall")
    public ResponseEntity<String> deleteAllPersons() {

        personService.deleteAllPersons();

        return new ResponseEntity<String>("All persons deleted",HttpStatus.OK);
    }
}
