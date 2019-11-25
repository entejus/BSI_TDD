package com.mielniczuk;

import javax.naming.SizeLimitExceededException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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


    public String getFirstName() {
        return this.firstName;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getIndexNr() {
        return this.indexNr;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setIndexNr(String indexNr) {
        this.indexNr = indexNr;
    }

    public LinkedHashMap<String, Double> getGradesMap() {
        return this.grades;
    }

    public ArrayList<Double> getGrades() {
        return (ArrayList<Double>) this.grades.values();
    }

    public LinkedHashMap<String, Boolean> getPresenceList() {
        return this.presenceList;
    }

    public void addGrade(String addingDate, Double grade) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        format.setLenient(false);
        try {
            format.parse(addingDate);
            if (VALID_GRADES.contains(grade)) {
                grades.put(addingDate, grade);
            } else {
                throw new IllegalArgumentException("Grade is not valid");
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date is not valid");
        }
    }

    public void editGrade(String dateString, Double newGrade) {
        if (VALID_GRADES.contains(newGrade) && grades.containsKey(dateString)) {
            grades.replace(dateString, newGrade);
        } else {
            throw new IllegalArgumentException("Grade or date is not valid");
        }
    }

    public void deleteGrade(String dateString) {
        if (grades.containsKey(dateString)) {
            grades.remove(dateString);
        } else {
            throw new IllegalArgumentException("Date is not valid");
        }
    }

    public double countAverage() {
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

    public void setPresence(String meetingDate, Boolean presence) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setLenient(false);
        try {
            format.parse(meetingDate);
            if (presenceList.size() < 15)
                presenceList.put(meetingDate, presence);
            else {
                throw new IllegalArgumentException("Osiągnięto maksymalny limit spotkań");
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date is not valid");
        }
    }

    public Boolean checkPresence(String meetingDate) {
        if (presenceList.containsKey(meetingDate)) {
            return presenceList.get(meetingDate);
        } else throw new IllegalArgumentException("Nie istnieje spotkanie z tego dnia");
    }

    public void editPresence(String meetingDate, Boolean presence) {
        if (presenceList.containsKey(meetingDate)) {
            presenceList.replace(meetingDate, presence);
        } else throw new IllegalArgumentException("Nie istnieje spotkanie z tego dnia");
    }

    public int getAbsencesNumber() {
        int count = 0;
        for (Map.Entry<String, Boolean> pair : presenceList.entrySet()) {
            if (!pair.getValue()) {
                count++;
            }
        }
        return count;
    }

}
