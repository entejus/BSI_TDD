package com.mielniczuk;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;


@RunWith(JUnitParamsRunner.class)
public class StudentTest {

    private  Student student;
    private  Student studentWithGrades;
    private  String firstName = "Jan";
    private  String surname = "Kowalski";
    private  String indexNr = "D12333";


    @Before
    public void setupClass(){
        student = new Student(firstName,surname,indexNr);
        LinkedHashMap<String, Double> grades = new LinkedHashMap<>();
        grades.put("12-11-2019 12:39",4.5);
        grades.put("23-01-2019 14:29",2.0);
        studentWithGrades = new Student(firstName,surname,indexNr,grades);
    }


    @Test
    public void shouldBeAbleToCreateStudent(){
        Student s = new Student("Jan","Kowalski","D12333");
        assertNotNull(s);
    }
    @Test
    public void shouldReturnFirstName() {
        assertEquals(firstName,student.getFirstName());
    }
    @Test
    public void shouldReturnSurname() {
        assertEquals(surname,student.getSurname());
    }
    @Test
    public void shouldReturnIndexNr() {
        assertEquals(indexNr,student.getIndexNr());
    }
    @Test
    public void shouldSetFirstName(){
        Student s = new Student("Jan","Kowalski","D12333");
        s.setFirstName("Krzysztof");
        assertEquals(s.getFirstName(),"Krzysztof");
    }
    @Test
    public void shouldSetSurname(){
        Student s = new Student("Jan","Kowalski","D12333");
        s.setSurname("Nowak");
        assertEquals(s.getSurname(),"Nowak");
    }
    @Test
    public void shouldSetIndexNr(){
        Student s = new Student("Jan","Kowalski","D12333");
        s.setIndexNr("D22333");
        assertEquals(s.getIndexNr(),"D22333");
    }

    @Test
    public void shouldSetGrade() {
        Student s = new Student("Jan","Kowalski","D12333");
        String addingDateString = s.addGrade(5.0);
        assertEquals(s.getGrades().get(addingDateString),5.0,0.1);
    }
    @Test
    public void shouldReturnGradeAdditionDate(){
        Student s = new Student("Jan","Kowalski","D12333");
        String addingDateString = s.addGrade(5.0);
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        assertEquals(addingDateString,formatter.format(currentDate));
    }
    @Test(expected = IllegalArgumentException.class)
    @Parameters({"0","1","-1","6","5.5","2.2","3.9"})
    public void addGradeShouldAcceptValidGrades(Double grade){
        Student s = new Student("Jan","Kowalski","D12333");
        String addingDateString = s.addGrade(grade);
    }

    @Test
    @Parameters({"12-11-2019 12:39,3.5","23-01-2019 14:29,5.0"})
    public void shouldEditGrade(String dateString,Double newGrade){
        studentWithGrades.editGrade(dateString,newGrade);
        assertEquals(newGrade,studentWithGrades.getGrades().get(dateString));
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"12-11-2019 12:39,0","23-01-2019 14:29,-1",
            "12-11-2019 12:39,1","23-01-2019 14:29,5.5",
            "12-11-2019 12:39,2.2","23-01-2019 14:29,3.9"})
    public void editGradShouldAcceptValidGrades(String dateString,Double newGrade){
        studentWithGrades.editGrade(dateString,newGrade);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({" ,2","12-11-1998 12:00,5.0"})
    public void editGradShouldAcceptValidDate(String dateString,Double newGrade){
        studentWithGrades.editGrade(dateString,newGrade);
    }

    @Test
    @Parameters({"12-11-2019 12:39","23-01-2019 14:29"})
    public void shouldDeleteGrade(String dateString){
        studentWithGrades.deleteGrade(dateString);
        assertNull(studentWithGrades.getGrades().get(dateString));
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({" ","12-11-1998 12:00,44-111-32 50:12"})
    public void deleteGradShouldAcceptValidDate(String dateString){
        studentWithGrades.deleteGrade(dateString);
    }


}