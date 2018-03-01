package com.daitangroup.api.services;

import com.daitangroup.api.exception.CpfAlreadyRegisteredException;
import com.daitangroup.api.model.Person;
import com.daitangroup.api.repository.PersonRepository;
import com.daitangroup.api.repository.ProductionOrderRepository;
import com.daitangroup.api.repository.VehicleRepository;
import org.junit.Before;
import org.junit.Test;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GarageServiceTest {

    PersonRepository personRepository = mock(PersonRepository.class);
    VehicleRepository vehicleRepository = mock(VehicleRepository.class);
    ProductionOrderRepository productionOrderRepository = mock(ProductionOrderRepository.class);
    GarageService garageService = new GarageService(personRepository,productionOrderRepository,vehicleRepository);

    @Test(expected = CpfAlreadyRegisteredException.class)
    public void testWhenCpfAlreadyRegisteredThrowException() throws CpfAlreadyRegisteredException {

        Person existingPerson = new Person();
        existingPerson.setCpf("22");
        when(personRepository.findByCpf("22")).thenReturn(Optional.of(existingPerson));

        Person newPerson = new Person();
        newPerson.setCpf("22");

        garageService.createPerson(newPerson);
    }

    @Test
    public void testRetrieveAPersonNotPreviouslyStoredReturnsEmpty() {

        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertEquals(Optional.empty(),garageService.findPersonById(1));
    }

    @Test
    public void testCanRetrieveAPersonPreviouslyStored() {

        Person foundPerson = new Person();
        foundPerson.setId(3L);
        when(personRepository.findById(3L)).thenReturn(Optional.of(foundPerson));

        assertEquals(Optional.of(foundPerson), garageService.findPersonById(3L));
    }
}