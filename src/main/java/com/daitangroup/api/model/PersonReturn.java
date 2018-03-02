package com.daitangroup.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonReturn {

    @JsonProperty
    private Person person;

    @JsonProperty
    private String errorMessage;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
