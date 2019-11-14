package com.mielniczuk;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;



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

}