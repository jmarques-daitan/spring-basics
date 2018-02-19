package com.daitangroup.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "production_order")
public class ProductionOrder {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty
    @Column(name = "description")
    private String description;

    @JsonProperty
    @Column(name = "completion_forecast")
    private String completionForecast;

    @JsonProperty
    @Column(name = "isFinihed")
    private Boolean isFinished;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompletionForecast() {
        return completionForecast;
    }

    public void setCompletionForecast(String completionForecast) {
        this.completionForecast = completionForecast;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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
        if ( !(o instanceof ProductionOrder) ) return false;
        ProductionOrder that = (ProductionOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(completionForecast, that.completionForecast) &&
                Objects.equals(isFinished, that.isFinished) &&
                Objects.equals(vehicle, that.vehicle) &&
                Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, completionForecast, isFinished, vehicle, person);
    }

    @Override
    public String toString() {
        return "ProductionOrder{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completionForecast='" + completionForecast + '\'' +
                ", isFinished=" + isFinished +
                ", vehicle=" + vehicle +
                ", person=" + person +
                '}';
    }
}
