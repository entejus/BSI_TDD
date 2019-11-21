package com.mielniczuk;

import javax.naming.SizeLimitExceededException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Student {
    private static final ArrayList<Double> VALID_GRADES = new ArrayList<>(Arrays.asList(2.0, 3.0, 3.5, 4.0, 4.5, 5.0));

    private String firstName, surname, indexNr;
    private LinkedHashMap<String, Double> grades;
    private LinkedHashMap<String, Boolean> presenceList;

    public Student(String firstName, String surname, String indexNr) {
        this.firstName = firstName;
        this.surname = surname;
        this.indexNr = indexNr;
        this.grades = new LinkedHashMap<>();
        this.presenceList = new LinkedHashMap<>();
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

    LinkedHashMap<String, Double> getGrades() {
        return this.grades;
    }

    LinkedHashMap<String, Boolean> getPresenceList() {
        return this.presenceList;
    }

    void addGrade(String addingDate, Double grade) {
        if (VALID_GRADES.contains(grade)) {
            grades.put(addingDate, grade);
        } else {
            throw new IllegalArgumentException("Grade is not valid");
        }
    }

    void editGrade(String dateString, Double newGrade) {
        if (VALID_GRADES.contains(newGrade) && grades.containsKey(dateString)) {
            grades.replace(dateString, newGrade);
        } else {
            throw new IllegalArgumentException("Grade or date is not valid");
        }
    }

    void deleteGrade(String dateString) {
        if (grades.containsKey(dateString)) {
            grades.remove(dateString);
        } else {
            throw new IllegalArgumentException("Date is not valid");
        }
    }

    double countAverage() {
        double average = 0;
        double gradesSum = 0;
        if (!grades.isEmpty()) {
            for (Map.Entry<String, Double> pair : grades.entrySet()) {
                gradesSum += pair.getValue();
            }
            average = gradesSum / grades.size();
        }
        return average;
    }

    void setPresence(String meetingDate, Boolean presence) {
        if (presenceList.size() < 15)
            presenceList.put(meetingDate, presence);
        else {
            throw new IllegalArgumentException("Osiągnięto maksymalny limit spotkań");
        }
    }

    Boolean checkPresence(String meetingDate) {
        if (presenceList.containsKey(meetingDate)) {
            return presenceList.get(meetingDate);
        } else throw new IllegalArgumentException("Nie istnieje spotkanie z tego dnia");
    }


}
