package com.mielniczuk;

import java.util.ArrayList;

public class Diary {
    private ArrayList<Student> studentList;

    public Diary() {
        this.studentList = new ArrayList<>();
    }

    public int getStudentsNumber() {
        return studentList.size();
    }

    public void addStudent(Student student) {
        ArrayList<String> indexNumbers = new ArrayList<>();
        for (Student s : studentList) {
            indexNumbers.add(s.getIndexNr());
        }
        if (indexNumbers.contains(student.getIndexNr())) {
            throw new IllegalArgumentException("Student o tym numerze indeksu jest już na liście");
        } else {
            studentList.add(student);
        }
    }

    public void deleteStudent(String indexNr) {
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr))) {
            studentList.removeIf(student -> student.getIndexNr().equals(indexNr));
        } else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }

    public void editStudentFirstName(String indexNr,String newFirstName){
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr)))
        {for(Student student  : studentList){
            if(student.getIndexNr().equals(indexNr)){
                student.setFirstName(newFirstName);
            }
        }}else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }

    public void editStudentSurname(String indexNr,String newSurname){
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr)))
        {for(Student student  : studentList){
            if(student.getIndexNr().equals(indexNr)){
                student.setSurname(newSurname);
            }
        }}else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }

}
