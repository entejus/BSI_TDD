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

}