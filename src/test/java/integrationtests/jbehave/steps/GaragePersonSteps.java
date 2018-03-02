package integrationtests.jbehave.steps;

import com.daitangroup.api.exception.CpfAlreadyRegisteredException;
import com.daitangroup.api.model.Person;
import com.daitangroup.api.services.GarageService;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GaragePersonSteps {

    @Autowired
    private GarageService garageService;

    private Person person;

    @Given("a person named $name with the cpf $cpf")
    public void createAPerson(String name, String cpf) {
        person = new Person();
        person.setFirstName(name);
        person.setCpf(cpf);
    }

    @When("I save this person on the database")
    public void saveThePerson() {
        try {
            garageService.createPerson(person);
        } catch (CpfAlreadyRegisteredException e) {
            fail("Error saving the person");
        }
    }

    @Then("I retrive this person passing the cpf $cpf")
    public void retriveThePerson(String cpf) {
        Optional<Person> personRetrived = garageService.findPersonByCpf(cpf);

            assertTrue(personRetrived.isPresent());
            assertEquals(person, personRetrived.get());
    }
}
