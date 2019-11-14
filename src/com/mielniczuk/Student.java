package com.mielniczuk;

public class Student {
    private String firstName, surname, indexNr;

    public Student(String firstName, String surname, String indexNr) {
        this.firstName = firstName;
        this.surname = surname;
        this.indexNr = indexNr;
    }

    String getFirstName() {
        return this.firstName;
    }

    String getSurname() {
        return this.surname;
    }

    String getIndexNr() {
        return this.indexNr;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    void setSurname(String surname) {
        this.surname = surname;
    }

    void setIndexNr(String indexNr) {
        this.indexNr = indexNr;
    }
}
