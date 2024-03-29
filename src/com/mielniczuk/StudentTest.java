package com.mielniczuk;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import static org.junit.Assert.*;


@RunWith(JUnitParamsRunner.class)
public class StudentTest {

    private Student student;
    private Student studentWithGrades;
    private Student studentWithPresenceList;
    private LinkedHashMap<String, Double> exampleGrades;
    private LinkedHashMap<String, Boolean> examplePresenceList;
    private ArrayList<String> meetingDates;
    private String firstName = "Jan";
    private String surname = "Kowalski";
    private String indexNr = "D12333";


    @Before
    public void setupClass() {
        student = new Student(firstName, surname, indexNr);

        exampleGrades = new LinkedHashMap<>();
        exampleGrades.put("12-11-2019 12:39", 4.5);
        exampleGrades.put("23-01-2019 14:29", 2.0);

        examplePresenceList = new LinkedHashMap<>();
        examplePresenceList.put("23-01-2019", true);
        examplePresenceList.put("24-01-2019", true);
        examplePresenceList.put("25-01-2019", false);
        examplePresenceList.put("26-01-2019", true);

        meetingDates = new ArrayList<>();
        for (Map.Entry<String, Boolean> pair : examplePresenceList.entrySet()) {
            meetingDates.add(pair.getKey());
        }

        //given
        studentWithGrades = new Student("Jan", "Kowalski", "D12333");
        for (Map.Entry<String, Double> pair : exampleGrades.entrySet()) {
            studentWithGrades.addGrade(pair.getKey(), pair.getValue());
        }
        studentWithPresenceList = new Student("Jan", "Kowalski", "D12333");
        for (Map.Entry<String, Boolean> pair : examplePresenceList.entrySet()) {
            studentWithPresenceList.setPresence(pair.getKey(), pair.getValue());
        }

    }



    @Test
    public void shouldBeAbleToCreateStudent() {
        Student s = new Student("Jan", "Kowalski", "D12333");
        assertNotNull(s);
    }

    @Test
    public void shouldReturnFirstName() {
        assertEquals(firstName, student.getFirstName());
    }

    @Test
    public void shouldReturnSurname() {
        assertEquals(surname, student.getSurname());
    }

    @Test
    public void shouldReturnIndexNr() {
        assertEquals(indexNr, student.getIndexNr());
    }

    @Test
    public void shouldSetFirstName() {
        //given
        Student s = new Student("Jan", "Kowalski", "D12333");

        //when
        s.setFirstName("Krzysztof");

        //then
        assertEquals(s.getFirstName(), "Krzysztof");
    }

    @Test
    public void shouldSetSurname() {
        //given
        Student s = new Student("Jan", "Kowalski", "D12333");

        //when
        s.setSurname("Nowak");

        //then
        assertEquals(s.getSurname(), "Nowak");
    }

    @Test
    public void shouldSetIndexNr() {
        //given
        Student s = new Student("Jan", "Kowalski", "D12333");

        //when
        s.setIndexNr("D22333");

        //then
        assertEquals(s.getIndexNr(), "D22333");
    }

    @Test
    @Parameters({"12-11-2019 12:39,3.5", "23-01-2019 14:29,5.0"})
    public void shouldAddGrade(String dateString, Double grade) {
        //given
        Student s = new Student("Jan", "Kowalski", "D12333");

        //when
        s.addGrade(dateString, grade);

        //then
        assertEquals(s.getGradesMap().get(dateString), grade, 0.1);
    }


    @Test(expected = IllegalArgumentException.class)
    @Parameters({"0", "1", "-1", "6", "5.5", "2.2", "3.9"})
    public void addGradeShouldAcceptValidGrades(Double grade) {
        //given
        Student s = new Student("Jan", "Kowalski", "D12333");
        String date = "12-11-2019 12:39";
        s.addGrade(date, grade);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"","1-1-2 12;39","111-23-2 223;12","11/12/1997 12:53"})
    public void addGradeShouldAcceptValidDate(String date) {
        //given
        Student s = new Student("Jan", "Kowalski", "D12333");
        Double grade = 4.0;
        s.addGrade(date, grade);
    }

    @Test
    @Parameters({"12-11-2019 12:39,3.5", "23-01-2019 14:29,5.0"})
    public void shouldEditGrade(String dateString, Double newGrade) {
        //when
        studentWithGrades.editGrade(dateString, newGrade);

        //then
        assertEquals(newGrade, studentWithGrades.getGradesMap().get(dateString));
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"12-11-2019 12:39,0", "23-01-2019 14:29,-1",
            "12-11-2019 12:39,1", "23-01-2019 14:29,5.5",
            "12-11-2019 12:39,2.2", "23-01-2019 14:29,3.9"})
    public void editGradShouldAcceptValidGrades(String dateString, Double newGrade) {
        //when
        studentWithGrades.editGrade(dateString, newGrade);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({" ,2", "12-11-1998 12:00,5.0"})
    public void editGradShouldAcceptValidDate(String dateString, Double newGrade) {
        //when
        studentWithGrades.editGrade(dateString, newGrade);
    }

    @Test
    @Parameters({"12-11-2019 12:39", "23-01-2019 14:29"})
    public void shouldDeleteGrade(String dateString) {
        //when
        studentWithGrades.deleteGrade(dateString);

        //then
        assertNull(studentWithGrades.getGradesMap().get(dateString));
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({" ", "12-11-1998 12:00,44-111-32 50:12"})
    public void deleteGradShouldAcceptValidDate(String dateString) {
        //when
        studentWithGrades.deleteGrade(dateString);
    }

    private Object[] getDoubleArray() {
        return new Object[]{
                new Object[]{Arrays.asList(4.5, 2.0, 5.0, 3.5, 4.0), 3.8},
                new Object[]{Arrays.asList(2.0, 2.0, 2.0, 2.0, 2.0), 2.0},
                new Object[]{Arrays.asList(4.0, 2.0, 5.0, 4.5, 2.0), 3.5},
                new Object[]{Arrays.asList(4.5, 4.0, 5.0, 4.5, 4.0), 4.4},
        };
    }

    @Test
    @Parameters(method = "getDoubleArray")
    public void shouldCountAverage(List<Double> grades, Double expectedAverage) {
        //given
        LinkedHashMap<String, Double> gradesMap = new LinkedHashMap<>();
        for (int i = 0; i < grades.size(); i++) {
            gradesMap.put("12-11-2019 12:3" + i, grades.get(i));
        }
        Student s = new Student("Jan", "Kowalski", "D12333");
        for (Map.Entry<String, Double> pair : gradesMap.entrySet()) {
            s.addGrade(pair.getKey(), pair.getValue());
        }

        //then
        assertEquals(expectedAverage, s.countAverage(), 0.01);
    }

    @Test
    @Parameters({"12-11-2019,true", "23-01-2019,false"})
    public void shouldSetPressence(String meetingDate, Boolean expectedPresence) {
        //given
        Student s = new Student("Jan", "Kowalski", "D12333");

        //when
        s.setPresence(meetingDate, expectedPresence);

        //then
        assertEquals(expectedPresence, s.getPresenceList().get(meetingDate));
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"","1-126-2","111-23-2","11/12/1997"})
    public void setPresenceShouldAcceptValidDate(String date) {
        //given
        Student s = new Student("Jan", "Kowalski", "D12333");
        s.setPresence(date, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldSetMaxFixedPresence() {
        Student s = new Student("Jan", "Kowalski", "D12333");
        for (int i = 1; i <= 16; i++) {
            s.setPresence(i + "-11-2019", true);
        }
    }

    @Test
    @Parameters({"12-11-2019,true", "23-01-2019,false"})
    public void shouldCheckPresence(String meetingDate, Boolean expectedPresence) {
        //given
        Student s = new Student("Jan", "Kowalski", "D12333");
        s.setPresence(meetingDate, expectedPresence);

        //when
        Boolean checkedPresence = s.checkPresence(meetingDate);

        //then
        assertEquals(expectedPresence, checkedPresence);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldCheckPresenceForExistingMeetings() {
        //given
        Student s = new Student("Jan", "Kowalski", "D12333");
        s.setPresence("12-11-2019", true);

        s.checkPresence("1-12-2019");
    }


    @Test
    @Parameters({"24-01-2019, true","24-01-2019, false","25-01-2019, true","25-01-2019, false"})
    public void shouldEditPresence(String meetingDate, Boolean presence) {
        //when
        studentWithPresenceList.editPresence(meetingDate, presence);

        //then
        assertEquals(presence, studentWithPresenceList.checkPresence(meetingDate));
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({" ,true", "12-11-1998 12:00,false"})
    public void editPresenceShouldAcceptValidDate(String meetingDate, Boolean presence){
        studentWithPresenceList.editPresence(meetingDate, presence);
    }

    @Test
    @Parameters({"6,4","0,0","2,1","2,2","5,0"})
    public void shouldCountAbsences(int meetingsNumber,int expectedAbsencesNumber){
        //given
        Student s = new Student("Jan", "Kowalski", "D12333");
        int j=0;
        for(int i=0;i<meetingsNumber;i++){
            if(j<expectedAbsencesNumber){
            s.setPresence((i+1)+"-11-2019",false);
            j++;
            }
            else {
                s.setPresence((i+1)+"-11-2019",true);
            }
        }

        //when
        int absencesNumber = s.countAbsences();

        //then
        assertEquals(expectedAbsencesNumber,absencesNumber);
    }
}