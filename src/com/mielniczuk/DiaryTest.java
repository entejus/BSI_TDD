package com.mielniczuk;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(JUnitParamsRunner.class)
public class DiaryTest {

    private Diary diary;
    private ArrayList<Student> mockedStudentList;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

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
        assertEquals(expectedNumberAfterDelete, numberAfterDelete);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"D55555", "D11111", "0"})
    public void shouldDeleteValidStudent(String indexToDelete) {
        //given
        for (Student s : mockedStudentList)
            diary.addStudent(s);

        diary.deleteStudent(indexToDelete);
    }

    @Test
    @Parameters({"D12333", "D86784", "D12433"})
    public void shouldEditStudentFirstName(String indexToEdit) {
        //given
        Student mockStudentEdit = null;
        for (Student s : mockedStudentList) {
            if (s.getIndexNr().equals(indexToEdit))
                mockStudentEdit = s;
            diary.addStudent(s);
        }
        //when
        diary.editStudentFirstName(indexToEdit, "Jan");

        //then
        verify(mockStudentEdit).setFirstName("Jan");
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"D55555", "D11111", "0"})
    public void shouldEditValidStudentFirstName(String indexToEdit) {
        //given
        for (Student s : mockedStudentList) {
            diary.addStudent(s);
        }
        //when
        diary.editStudentFirstName(indexToEdit, "Jan");
    }


    @Test
    @Parameters({"D12333", "D86784", "D12433"})
    public void shouldEditStudentSurname(String indexToEdit) {
        //given
        Student mockStudentEdit = null;
        for (Student s : mockedStudentList) {
            if (s.getIndexNr().equals(indexToEdit))
                mockStudentEdit = s;
            diary.addStudent(s);
        }
        //when
        diary.editStudentSurname(indexToEdit, "Kowalski");

        //then
        verify(mockStudentEdit).setSurname("Kowalski");
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"D55555", "D11111", "0"})
    public void shouldEditValidStudentSurname(String indexToEdit) {
        //given
        for (Student s : mockedStudentList) {
            diary.addStudent(s);
        }
        //when
        diary.editStudentSurname(indexToEdit, "Kowalski");
    }

    @Test
    @Parameters({"D12333,12-11-2019,true", "D86784,23-01-2019,false",
            "D12433,23-01-2019,false", "D12333,23-01-2019,false"})
    public void shouldSetStudentPresence(String studentIndex, String meetingDate, Boolean presence) {
        //given
        Student editedMockStudent = null;
        for (Student s : mockedStudentList) {
            if (s.getIndexNr().equals(studentIndex))
                editedMockStudent = s;
            diary.addStudent(s);
        }
        //when
        diary.setStudentPresence(studentIndex, meetingDate, presence);

        //then
        verify(editedMockStudent).setPresence(meetingDate, presence);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"D55555,12-11-2019,true", "D11111,12-11-2019,true", "0,12-11-2019,true"})
    public void shouldEditValidStudentSurname(String studentIndex, String meetingDate, Boolean presence) {
        //given
        for (Student s : mockedStudentList) {
            diary.addStudent(s);
        }
        //when
        diary.setStudentPresence(studentIndex, meetingDate, presence);
    }

    @Test
    @Parameters({"D12333,12-11-2019,true", "D86784,23-01-2019,true",
            "D12433,23-01-2019,false", "D12333,23-01-2019,false"})
    public void shouldCheckStudentPresence(String studentIndex, String meetingDate, Boolean expectedPresence) {
        //given
        Student editedMockStudent = null;
        for (Student s : mockedStudentList) {
            if (s.getIndexNr().equals(studentIndex))
                editedMockStudent = s;
            diary.addStudent(s);
        }
        when(editedMockStudent.checkPresence(meetingDate)).thenReturn(expectedPresence);
        //when
        Boolean studentPresence = diary.checkStudentPresence(studentIndex, meetingDate);

        //then
        verify(editedMockStudent).checkPresence(meetingDate);
        assertEquals(expectedPresence, studentPresence);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"D55555,12-11-2019", "D11111,12-11-2019", "0,12-11-2019"})
    public void shouldCheckValidStudentPresence(String studentIndex, String meetingDate) {
        //given
        for (Student s : mockedStudentList) {
            diary.addStudent(s);
        }
        //when
        diary.checkStudentPresence(studentIndex, meetingDate);
    }

    @Test
    @Parameters({"D12333,12-11-2019,false", "D86784,23-01-2019,false",
            "D12433,23-01-2019,true", "D12333,23-01-2019,true"})
    public void shouldEditStudentPresence(String studentIndex, String meetingDate, Boolean newPresence) {
        //given
        Student editedMockStudent = null;
        for (Student s : mockedStudentList) {
            if (s.getIndexNr().equals(studentIndex))
                editedMockStudent = s;
            diary.addStudent(s);
        }
        //when
        diary.editStudentPresence(studentIndex, meetingDate, newPresence);

        //then
        verify(editedMockStudent).editPresence(meetingDate, newPresence);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"D55555,12-11-2019,true", "D11111,12-11-2019,false", "0,12-11-2019,true"})
    public void shouldEditValidStudentPresence(String studentIndex, String meetingDate, Boolean newPresence) {
        //given
        for (Student s : mockedStudentList) {
            diary.addStudent(s);
        }
        //when
        diary.editStudentPresence(studentIndex, meetingDate, newPresence);
    }

    @Test
    @Parameters({"D12333,12-11-2019 12:39,3.5", "D86784,23-01-2019 14:29,5.0"})
    public void shouldAddStudentGrade(String studentIndex, String addingDate, Double grade) {
        //given
        Student editedMockStudent = null;
        for (Student s : mockedStudentList) {
            if (s.getIndexNr().equals(studentIndex))
                editedMockStudent = s;
            diary.addStudent(s);
        }
        //when
        diary.addStudentGrade(studentIndex, addingDate, grade);

        //then
        verify(editedMockStudent).addGrade(addingDate, grade);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"D55555,12-11-2019 12:39,3.5", "0,23-01-2019 14:29,5.0"})
    public void shouldAddValidStudentGrade(String studentIndex, String addingDate, Double grade) {
        //given
        for (Student s : mockedStudentList) {
            diary.addStudent(s);
        }
        //when
        diary.addStudentGrade(studentIndex, addingDate, grade);
    }

    @Test
    @Parameters({"D12333,12-11-2019 12:39,3.5", "D86784,23-01-2019 14:29,5.0"})
    public void shouldEditStudentGrade(String studentIndex, String gradeDate, Double newGrade) {
        //given
        Student editedMockStudent = null;
        for (Student s : mockedStudentList) {
            if (s.getIndexNr().equals(studentIndex))
                editedMockStudent = s;
            diary.addStudent(s);
        }
        //when
        diary.editStudentGrade(studentIndex, gradeDate, newGrade);

        //then
        verify(editedMockStudent).editGrade(gradeDate, newGrade);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"D55555,12-11-2019 12:39,3.5", "0,23-01-2019 14:29,5.0"})
    public void shouldEditValidStudentGrade(String studentIndex, String gradeDate, Double newGrade) {
        //given
        for (Student s : mockedStudentList) {
            diary.addStudent(s);
        }
        //when
        diary.editStudentGrade(studentIndex, gradeDate, newGrade);
    }

    @Test
    @Parameters({"D12333,12-11-2019 12:39", "D86784,23-01-2019 14:29"})
    public void shouldDeleteStudentGrade(String studentIndex, String gradeDate) {
        //given
        Student editedMockStudent = null;
        for (Student s : mockedStudentList) {
            if (s.getIndexNr().equals(studentIndex))
                editedMockStudent = s;
            diary.addStudent(s);
        }
        //when
        diary.deleteStudentGrade(studentIndex, gradeDate);

        //then
        verify(editedMockStudent).deleteGrade(gradeDate);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"D55555,12-11-2019 12:39", "0,23-01-2019 14:29"})
    public void shouldDeleteValidStudentGrade(String studentIndex, String gradeDate) {
        //given
        for (Student s : mockedStudentList) {
            diary.addStudent(s);
        }
        //when
        diary.deleteStudentGrade(studentIndex, gradeDate);
    }

    @Test
    @Parameters({"D12333,4.23", "D86784,3.06", "D12433,4.5"})
    public void shouldCountStudentAverage(String studentIndex, Double expectedStudentAverage) {
        //given
        Student editedMockStudent = null;
        for (Student s : mockedStudentList) {
            if (s.getIndexNr().equals(studentIndex))
                editedMockStudent = s;
            diary.addStudent(s);
        }

        when(editedMockStudent.countAverage()).thenReturn(expectedStudentAverage);
        //when
        Double studentAverage = diary.countStudentAverage(studentIndex);

        //then
        verify(editedMockStudent).countAverage();
        assertEquals(expectedStudentAverage, studentAverage);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"D55555", "0"})
    public void shouldCountValidStudentAverage(String studentIndex) {
        //given
        for (Student s : mockedStudentList) {
            diary.addStudent(s);
        }
        //when
        diary.countStudentAverage(studentIndex);
    }

    private Object[] getSampleStudentsGrades() {
        return new Object[]{
                new Object[]{Arrays.asList(Arrays.asList(4.5, 2.0),
                        Arrays.asList(4.5, 5.0, 4.5),
                        Arrays.asList(4.5, 2.0, 3.5,2.0),
                        Arrays.asList(4.5, 2.0, 3.0, 3.5)), 3.5},
                new Object[]{Arrays.asList(Arrays.asList(3.5, 2.0),
                        Arrays.asList(3.0,2.0),
                        Arrays.asList(3.5,5.0),
                        Arrays.asList(3.5, 3.0)), 3.19},
                new Object[]{Arrays.asList(Arrays.asList(4.5, 5.0),
                        Arrays.asList(4.5, 5.0, 4.5, 2.0),
                        Arrays.asList(3.5, 2.0),
                        Arrays.asList(4.5, 3.0, 3.5)), 3.82},
        };
    }

    @Test
    @Parameters(method = "getSampleStudentsGrades")
    public void shouldCountGroupAverage(List<List<Double>> studentsGrades, Double expectedAverage) {
        //given
        int i = 0;
        for (Student s : mockedStudentList) {
            when(s.getGrades()).thenReturn(new ArrayList<>(studentsGrades.get(i)));
            i++;
            diary.addStudent(s);
        }
        //when
        Double groupAverage = diary.countGroupAverage();

        //then
        for (Student s : mockedStudentList) {
            verify(s).getGrades();
        }
        assertEquals(groupAverage, expectedAverage,0.01);
    }

    @Test
    @Parameters({"D12333,4", "D86784,2", "D12433,0"})
    public void shouldCountStudentAbsences(String studentIndex,int expectedStudentAbsences){
        //given
        Student editedMockStudent = null;
        for (Student s : mockedStudentList) {
            if (s.getIndexNr().equals(studentIndex))
                editedMockStudent = s;
            diary.addStudent(s);
        }

        when(editedMockStudent.countAbsences()).thenReturn(expectedStudentAbsences);
        //when
        int studentAbsences = diary.countStudentAbsences(studentIndex);

        //then
        verify(editedMockStudent).countAbsences();
        assertEquals(expectedStudentAbsences, studentAbsences);
    }


    @Test(expected = IllegalArgumentException.class)
    @Parameters({"D55555", "0"})
    public void shouldCountValidStudentAbsences(String studentIndex) {
        //given
        for (Student s : mockedStudentList) {
            diary.addStudent(s);
        }
        //when
        diary.countStudentAbsences(studentIndex);
    }

    @Test
    public void shouldExportDataToFile() throws IOException {
        //given
        LinkedHashMap<String,Double> grades = new LinkedHashMap<>();
        grades.put("12-11-2019 12:39",3.5);
        grades.put("23-01-2019 14:29",5.0);

        LinkedHashMap<String,Boolean> presenceList= new LinkedHashMap<>();
        presenceList.put("12-11-2019",true);
        presenceList.put("15-11-2019",true);
        presenceList.put("20-11-2019",false);

        for (Student s : mockedStudentList) {
            diary.addStudent(s);
            when(s.getFirstName()).thenReturn("Jan");
            when(s.getSurname()).thenReturn("Kowalski");
            when(s.getGradesMap()).thenReturn(grades);
            when(s.getPresenceList()).thenReturn(presenceList);
        }

        final File exportFile = folder.newFile("export.txt");
        diary.exportDataToFile(exportFile);
        assertTrue(FileUtils.sizeOf(exportFile)>0);
    }




    @Test
    public void shouldImportDataFromFile() throws IOException {
        final File importFile = folder.newFile("import.txt");

        String json="[{\"firstName\":\"Jan\",\"indexNr\":\"D12333\",\"surname\":\"Kowalski\"" +
                ",\"presenceList\":[{\"12-11-2019\":true},{\"15-11-2019\":true},{\"20-11-2019\":false}],\"grades\"" +
                ":[{\"12-11-2019 12:39\":3.5},{\"23-01-2019 14:29\":5}]},{\"firstName\":\"Jan\",\"indexNr\":\"D86784\"" +
                ",\"surname\":\"Kowalski\",\"presenceList\":[{\"12-11-2019\":true},{\"15-11-2019\":true},{\"20-11-2019\"" +
                ":false}],\"grades\":[{\"12-11-2019 12:39\":3.5},{\"23-01-2019 14:29\":5}]},{\"firstName\":\"Jan\",\"" +
                "indexNr\":\"D12433\",\"surname\":\"Kowalski\",\"presenceList\":[{\"12-11-2019\":true},{\"15-11-2019\"" +
                ":true},{\"20-11-2019\":false}],\"grades\":[{\"12-11-2019 12:39\":3.5},{\"23-01-2019 14:29\":5}]}," +
                "{\"firstName\":\"Jan\",\"indexNr\":\"D00333\",\"surname\":\"Kowalski\",\"presenceList\":[{\"12-11-2019\"" +
                ":true},{\"15-11-2019\":true},{\"20-11-2019\":false}],\"grades\":[{\"12-11-2019 12:39\":3.5}," +
                "{\"23-01-2019 14:29\":5}]}]";

        FileUtils.writeStringToFile(importFile,json);

        diary.importDataFromFile(importFile);


    }

}
