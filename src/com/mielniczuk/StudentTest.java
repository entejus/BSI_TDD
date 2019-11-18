package com.mielniczuk;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;


@RunWith(JUnitParamsRunner.class)
public class StudentTest {

    private Student student;


    @Test
    public void shouldBeAbleToCreateStudent(){
        Student s = new Student("Jan","Kowalski","D12333");
        assertNotNull(s);
    }
    @Test
    public void shouldReturnFirstName() {
        Student s = new Student("Jan","Kowalski","D12333");
        assertEquals("Jan",s.getFirstName());
    }
    @Test
    public void shouldReturnSurname() {
        Student s = new Student("Jan","Kowalski","D12333");
        assertEquals("Kowalski",s.getSurname());
    }
    @Test
    public void shouldReturnIndexNr() {
        Student s = new Student("Jan","Kowalski","D12333");
        assertEquals("D12333",s.getIndexNr());
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
        Date addingDate = s.addGrade(5.0);
        assertEquals(s.grades.get(addingDate),5.0,0.1);
    }
    @Test
    public void shouldReturnGradeAdditionDate(){
        Student s = new Student("Jan","Kowalski","D12333");
        Date addingDate = s.addGrade(5.0);
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        assertEquals(formatter.format(addingDate),formatter.format(currentDate));
    }
    @Test(expected = IllegalArgumentException.class)
    @Parameters({"0","1","-1","6","5.5","2.2","3.9"})
    public void shouldAcceptValidGrades(Double grade){
        Student s = new Student("Jan","Kowalski","D12333");
        Date addingDate = s.addGrade(grade);
    }

}