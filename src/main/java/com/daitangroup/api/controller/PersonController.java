package com.daitangroup.api.controller;

import com.daitangroup.api.exception.CpfAlreadyRegisteredException;
import com.daitangroup.api.model.Person;
import com.daitangroup.api.model.PersonReturn;
import com.daitangroup.api.services.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private GarageService garageService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable> findAllPersons() {

        Iterable<Person> personsResult = garageService.findAllPersons();

        return new ResponseEntity<>(personsResult, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Person> findPersonById(@PathVariable Long id) {

        Optional<Person> personResult = garageService.findPersonById(id);

        if (personResult.isPresent()) {
            return new ResponseEntity<>(personResult.get(), HttpStatus.OK);
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
    public ResponseEntity<PersonReturn> createPerson(@RequestBody Person person) {

        PersonReturn personToReturn = new PersonReturn();

        try {
            Person personSaved = garageService.createPerson(person);
            personToReturn.setPerson(personSaved);
            return new ResponseEntity<>(personToReturn, HttpStatus.OK);

        } catch (CpfAlreadyRegisteredException ex) {

            personToReturn.setPerson(person);
            personToReturn.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(personToReturn, HttpStatus.CONFLICT);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/associate-vehicle")
    public ResponseEntity<Person> addVehicleToPerson(@RequestParam("personid") long personid, @RequestParam("vehicleid") long vehicleid) {

        Person person = garageService.addVehicleToPerson(personid, vehicleid);

        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update/{id}")
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

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete-all")
    public ResponseEntity<String> deleteAllPersons() {

        garageService.deleteAllPersons();

        return new ResponseEntity<String>("All persons deleted", HttpStatus.OK);
    }
}
