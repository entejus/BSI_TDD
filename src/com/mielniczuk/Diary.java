package com.mielniczuk;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

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

    public void deleteStudentGrade(String indexNr, String gradeDate) {
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

    public double countStudentAverage(String indexNr) {
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr))) {
            double studentAverage = 0.0;
            for (Student student : studentList) {
                if (student.getIndexNr().equals(indexNr)) {
                    studentAverage = student.countAverage();
                }
            }
            return studentAverage;
        } else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }

    public double countGroupAverage() {
        double groupAverage;
        double gradesSum = 0;
        ArrayList<Double> groupGrades = new ArrayList<>();
        for (Student student : studentList) {
            groupGrades.addAll(student.getGrades());
        }
        for (Double grade : groupGrades) {
            gradesSum += grade;
        }
        groupAverage = gradesSum / groupGrades.size();
        return groupAverage;
    }

    public int countStudentAbsences(String indexNr) {
        if (studentList.stream().anyMatch(student -> student.getIndexNr().equals(indexNr))) {
            int studentAbsences = 0;
            for (Student student : studentList) {
                if (student.getIndexNr().equals(indexNr)) {
                    studentAbsences = student.countAbsences();
                }
            }
            return studentAbsences;
        } else {
            throw new IllegalArgumentException("Studenta o tym numerze indeksu nie ma na liście");
        }
    }

    public void exportDataToFile(File exportFile) {
        JSONArray jsonDiary = new JSONArray();
        for (Student student : studentList) {
            JSONObject jsonStudent = new JSONObject();
            jsonStudent.put("indexNr", student.getIndexNr());
            jsonStudent.put("firstName", student.getFirstName());
            jsonStudent.put("surname", student.getSurname());

            JSONArray jsonGrades = new JSONArray();
            for (Map.Entry<String, Double> pair : student.getGradesMap().entrySet()) {
                JSONObject jsonGrade = new JSONObject();
                jsonGrade.put(pair.getKey(), pair.getValue());
                jsonGrades.put(jsonGrade);
            }
            jsonStudent.put("grades", jsonGrades);

            JSONArray jsonPresenceList = new JSONArray();
            for (Map.Entry<String, Boolean> pair : student.getPresenceList().entrySet()) {
                JSONObject jsonPresence = new JSONObject();
                jsonPresence.put(pair.getKey(), pair.getValue());
                jsonPresenceList.put(jsonPresence);
            }
            jsonStudent.put("presenceList", jsonPresenceList);
            jsonDiary.put(jsonStudent);
        }

        try (FileWriter file = new FileWriter(exportFile)) {
            file.write(jsonDiary.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + jsonDiary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importDataFromFile(File importFile) {
        try  {
            String jsonString = FileUtils.readFileToString(importFile);
            JSONArray jsonDiary = new JSONArray(jsonString);
            for (int i = 0; i < jsonDiary.length(); i++) {
                JSONObject jsonStudent = jsonDiary.getJSONObject(i);

                String firstName = jsonStudent.getString("firstName");
                String surname = jsonStudent.getString("surname");
                String indexNr = jsonStudent.getString("indexNr");

                addStudent(new Student(firstName,surname,indexNr));



                JSONArray jsonGrades = jsonStudent.getJSONArray("grades");
                for (int j = 0; j < jsonGrades.length(); j++) {
                    JSONObject jsonGrade = jsonGrades.getJSONObject(j);
                            String gradeDate = jsonGrade.keys().next();
                            Double grade = jsonGrade.getDouble(gradeDate);
                    addStudentGrade(indexNr,gradeDate,grade);

                }
                JSONArray jsonPresenceList = jsonStudent.getJSONArray("presenceList");
                for (int j = 0; j < jsonPresenceList.length(); j++) {
                    JSONObject jsonPresence = jsonPresenceList.getJSONObject(j);
                    String meetingDate = jsonPresence.keys().next();
                    Boolean presence = jsonPresence.getBoolean(meetingDate);
                    setStudentPresence(indexNr,meetingDate,presence);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
