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

        Optional<Person> personResult = personService.findPersonById(id);

        if (personResult.isPresent()) {
            return new ResponseEntity<>(personResult.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable> findAllPersons() {

        Iterable<Person> personsResult = personService.findAllPersons();

        if (personsResult.iterator().hasNext()) {
            return new ResponseEntity<>(personsResult, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cpf/{cpf}")
    public ResponseEntity<Person> findPersonByCpf(@PathVariable String cpf) {

        Optional<Person> personResult = personService.findPersonByCpf(cpf);

        if (personResult.isPresent()) {
            return new ResponseEntity<>(personResult.get(), HttpStatus.OK);
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
    public ResponseEntity<Person> addVehicleToPerson(@RequestParam("personid") long personid, @RequestParam("vehicleid") long vehicleid) {

        Person person = personService.addVehicleToPerson(personid, vehicleid);

        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/change/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable long id, @RequestBody Person person) {

        person.setId(id);
        Person personUpdated = personService.updatePerson(person);

        if(person != null) {
            return new ResponseEntity<>(personUpdated, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable long id) {

        Person personDeleted = personService.deletePerson(id);

        if(personDeleted != null){
            return new ResponseEntity<>(personDeleted, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteall")
    public ResponseEntity<String> deleteAllPersons() {

        personService.deleteAllPersons();

        return new ResponseEntity<String>("All persons deleted",HttpStatus.OK);
    }
}
