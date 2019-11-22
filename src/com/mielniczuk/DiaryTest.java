package com.mielniczuk;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(JUnitParamsRunner.class)
public class DiaryTest {

    private Diary diary;
    private ArrayList<Student> mockedStudentList;

    @Mock
    private Student mockStudent;
    @Mock
    private Student mockStudent1;
    @Mock
    private Student mockStudent2;
    @Mock
    private Student mockStudent3;
    @Mock
    private Student mockStudent4;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        diary = new Diary();

        when(mockStudent1.getIndexNr()).thenReturn("D12333");
        when(mockStudent2.getIndexNr()).thenReturn("D86784");
        when(mockStudent3.getIndexNr()).thenReturn("D12433");
        when(mockStudent4.getIndexNr()).thenReturn("D00333");

        mockedStudentList = new ArrayList<>();
        mockedStudentList.add(mockStudent1);
        mockedStudentList.add(mockStudent2);
        mockedStudentList.add(mockStudent3);
        mockedStudentList.add(mockStudent4);
    }

    @Test
    public void shouldAddStudentToList() {
        int expectedNumberAfterAdd = diary.getStudentsNumber() + 1;
        diary.addStudent(mockStudent);

        int numberAfterAdd = diary.getStudentsNumber();

        assertEquals(expectedNumberAfterAdd, numberAfterAdd);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddSameStudent() {
        when(mockStudent.getIndexNr()).thenReturn("D12333");
        diary.addStudent(mockStudent);

        diary.addStudent(mockStudent);
    }


    @Test
    @Parameters({"D12333", "D86784", "D12433"})
    public void shouldDeleteStudent(String indexToDelete) {
        //given
        for (Student s : mockedStudentList)
            diary.addStudent(s);
        int expectedNumberAfterDelete = diary.getStudentsNumber() - 1;

        //when
        diary.deleteStudent(indexToDelete);
        int numberAfterDelete = diary.getStudentsNumber();

        //then
        assertEquals(expectedNumberAfterDelete,numberAfterDelete);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"D55555", "D11111", "0"})
    public void shouldDeleteValidStudent(String indexToDelete){
        //given
        for (Student s : mockedStudentList)
            diary.addStudent(s);

        diary.deleteStudent(indexToDelete);
    }
}