package com.daitangroup.api.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty
    @Column(name = "plate")
    private String plate;

    @JsonProperty
    @Column(name = "model")
    private String model;

    @JsonProperty
    @Column(name = "color")
    private String color;

    @ManyToOne
    @JsonIgnoreProperties("vehicles")
    @JoinColumn(name = "person_id")
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( !(o instanceof Vehicle) ) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) &&
                Objects.equals(plate, vehicle.plate) &&
                Objects.equals(model, vehicle.model) &&
                Objects.equals(color, vehicle.color) &&
                Objects.equals(person, vehicle.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plate, model, color, person);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", plate='" + plate + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", person=" + person +
                '}';
    }
}
