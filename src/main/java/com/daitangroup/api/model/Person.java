package com.daitangroup.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty
    @Column(name = "first_name")
    private String firstName;

    @JsonProperty
    @Column(name = "last_name")
    private String lastName;

    @JsonProperty
    @Column(name = "cpf")
    private String cpf;

    @JsonProperty
    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Vehicle> vehicle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Vehicle> getVehicle() {
        return vehicle;
    }

    public void setVehicle(Set<Vehicle> vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( !(o instanceof Person) ) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(cpf, person.cpf) &&
                Objects.equals(phone, person.phone) &&
                Objects.equals(vehicle, person.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, cpf, phone, vehicle);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cpf='" + cpf + '\'' +
                ", phone='" + phone + '\'' +
                ", vehicle=" + vehicle +
                '}';
    }
}
