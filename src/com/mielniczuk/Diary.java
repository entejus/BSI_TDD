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

    public void editStudentFirstName(String indexNr, String newFirstName) {
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr))) {
            for (Student student : studentList) {
                if (student.getIndexNr().equals(indexNr)) {
                    student.setFirstName(newFirstName);
                }
            }
        } else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }

    public void editStudentSurname(String indexNr, String newSurname) {
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr))) {
            for (Student student : studentList) {
                if (student.getIndexNr().equals(indexNr)) {
                    student.setSurname(newSurname);
                }
            }
        } else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }

    public void setStudentPresence(String indexNr, String meetingDate, Boolean presence) {
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr))) {
            for (Student student : studentList) {
                if (student.getIndexNr().equals(indexNr)) {
                    student.setPresence(meetingDate, presence);
                }
            }
        } else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }

    public Boolean checkStudentPresence(String indexNr, String meetingDate) {
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr))) {
            Boolean studentPresence = null;
            for (Student student : studentList) {
                if (student.getIndexNr().equals(indexNr)) {
                    studentPresence = student.checkPresence(meetingDate);
                }
            }
            return studentPresence;
        } else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }

    public void editStudentPresence(String indexNr, String meetingDate, Boolean newPresence) {
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr))) {
            for (Student student : studentList) {
                if (student.getIndexNr().equals(indexNr)) {
                    student.editPresence(meetingDate, newPresence);
                }
            }
        } else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }

    public void addStudentGrade(String indexNr, String addingDate, Double grade) {
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr))) {
            for (Student student : studentList) {
                if (student.getIndexNr().equals(indexNr)) {
                    student.addGrade(addingDate, grade);
                }
            }
        } else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }

    public void editStudentGrade(String indexNr, String gradeDate, Double newGrade) {
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr))) {
            for (Student student : studentList) {
                if (student.getIndexNr().equals(indexNr)) {
                    student.editGrade(gradeDate, newGrade);
                }
            }
        } else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }

    public void deletStudentGrade(String indexNr, String gradeDate) {
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr))) {
            for (Student student : studentList) {
                if (student.getIndexNr().equals(indexNr)) {
                    student.deleteGrade(gradeDate);
                }
            }
        } else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }
}
