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

    public Optional<Person> findById(long id) {
        return personRepository.findById(id);
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findByCpf(String cpf) {
        return personRepository.findByCpf(cpf);
    }

    public ProductionOrder findPoByUserId(long id) {

        Optional<Person> person = personRepository.findById(id);

        if(person.isPresent()) {
            Optional<ProductionOrder> productionOrder = productionOrderRepository.findByPerson(person.get());
            if(productionOrder.isPresent()) {
                return productionOrder.get();
            }
        }

        return null;
    }

    public Iterable<Vehicle> findVehiclesByPersonId(long id) {

        Optional<Person> person = personRepository.findById(id);

        if(person.isPresent()) {
            return person.get().getVehicle();
        }

        return null;
    }

    public Person createPerson(Person person) {

        Optional<Person> personChecked = personRepository.findByCpf(person.getCpf());

        if(personChecked.isPresent()){
            return null;
        }

        return personRepository.save(person);
    }

    public Person associateVehicle(long personId, long vehicleId) {

        Optional<Person> person = personRepository.findById(personId);
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);

        if(person.isPresent() && vehicle.isPresent()) {
            Set<Vehicle> vehicles = person.get().getVehicle();
            Person personPresent = person.get();
            Vehicle vehiclePresent = vehicle.get();
            vehiclePresent.setPerson(personPresent);
            vehicles.add(vehicleRepository.save(vehiclePresent));
            personPresent.setVehicle(vehicles);
            return personRepository.save(personPresent);
        }

        return null;
    }

    public Person changePerson(long id, Person person) {

        Optional<Person> personToChange = personRepository.findById(id);

        if(personToChange.isPresent()) {
            Person personToSet = personToChange.get();

            if(person.getFirstName() != null) {
                personToSet.setFirstName(person.getFirstName());
            }

            if(person.getLastName() != null) {
                personToSet.setLastName(person.getLastName());
            }

            if(person.getCpf() != null) {
                personToSet.setCpf(person.getCpf());
            }

            if(person.getPhone() != null) {
                personToSet.setPhone(person.getPhone());
            }
            return personRepository.save(personToSet);
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
