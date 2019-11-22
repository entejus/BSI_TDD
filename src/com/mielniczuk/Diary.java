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

    public void deleteStudent(){

    }

}
