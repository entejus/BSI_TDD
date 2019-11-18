package com.mielniczuk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;

public class Student {
    private static final ArrayList<Double> VALID_GRADES = new ArrayList<>(Arrays.asList(2.0, 3.0, 3.5, 4.0, 4.5, 5.0));

    private String firstName, surname, indexNr;
    private LinkedHashMap<String, Double> grades;

    public Student(String firstName, String surname, String indexNr) {
        this.firstName = firstName;
        this.surname = surname;
        this.indexNr = indexNr;
        this.grades = new LinkedHashMap<>();
    }

    public Student(String firstName, String surname, String indexNr, LinkedHashMap<String, Double> grades) {
        this.firstName = firstName;
        this.surname = surname;
        this.indexNr = indexNr;
        this.grades= grades;
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

    LinkedHashMap<String, Double> getGrades(){
        return this.grades;
    }

    String addGrade(Double grade) {
        if (VALID_GRADES.contains(grade)) {
            Date currentDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String currentDateString = formatter.format(currentDate);
            grades.put(currentDateString, grade);
            return currentDateString;
        } else {
            throw new IllegalArgumentException("Grade is not valid");
        }
    }



    void editGrade(String dateString,Double newGrade){

    }


}
