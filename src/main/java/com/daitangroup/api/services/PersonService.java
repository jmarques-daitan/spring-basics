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
import java.util.Set;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public Optional<Person> findPersonById(long id) {
        return personRepository.findById(id);
    }

    public Iterable<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> findPersonByCpf(String cpf) {
        return personRepository.findByCpf(cpf);
    }

    public Person createPerson(Person person) {

        Optional<Person> personToCheck = personRepository.findByCpf(person.getCpf());

        if(personToCheck.isPresent()){
            return null;
        }

        return personRepository.save(person);
    }

    public Person addVehicleToPerson(long personId, long vehicleId) {

        Optional<Person> personResult = personRepository.findById(personId);
        Optional<Vehicle> vehicleResult = vehicleRepository.findById(vehicleId);

        if(personResult.isPresent() && vehicleResult.isPresent()) {
            Set<Vehicle> vehicles = personResult.get().getVehicles();
            Person personPresent = personResult.get();
            Vehicle vehiclePresent = vehicleResult.get();
            vehiclePresent.setPerson(personPresent);
            vehicleRepository.save(vehiclePresent);
//            vehicles.add(vehiclePresent);
//            personPresent.setVehicles(vehicles);
//            personRepository.save(personPresent);
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

}
