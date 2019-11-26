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
public class BowlingTest {

    private Bowling bowling;

    @Before
    public void setUp() {
        bowling = new Bowling();
    }

    @Test
    @Parameters({"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    public void shouldSetThrowPointsAfterThrow(int knockedPins) {
        bowling.ballThrow(knockedPins);

        assertEquals(knockedPins, bowling.getRoundPoints(0));
    }


    @Test(expected = IllegalArgumentException.class)
    @Parameters({"-1", "11", "10.5"})
    public void shouldAcceptValidKnockedPinsNumber(int knockedPins) {
        bowling.ballThrow(knockedPins);
    }


    private Object[] getRoundThrows() {
        return new Object[]{
                new Object[]{Arrays.asList(0, 10), 10},
                new Object[]{Arrays.asList(10), 10},
                new Object[]{Arrays.asList(1, 5), 6},
                new Object[]{Arrays.asList(0, 0), 0},
                new Object[]{Arrays.asList(7, 2), 9},
                new Object[]{Arrays.asList(5, 5), 10},
                new Object[]{Arrays.asList(2, 8), 10},
        };
    }

    @Test
    @Parameters(method = "getRoundThrows")
    public void shouldSetRoundPointsAfterThrow(List<Integer> knockedPinsList, int roundPoints) {
        for (Integer knockedPins : knockedPinsList) {
            bowling.ballThrow(knockedPins);
        }

        assertEquals(roundPoints, bowling.getRoundPoints(0));
    }

    private Object[] getInvalidRoundThrows() {
        return new Object[]{
                new Object[]{Arrays.asList(0, 11)},
                new Object[]{Arrays.asList(11)},
                new Object[]{Arrays.asList(5, 6)},
                new Object[]{Arrays.asList(7, 8)},
        };
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "getInvalidRoundThrows")
    public void shouldSetValidRoundPointsAfterThrow(List<Integer> knockedPinsList) {
        for (Integer knockedPins : knockedPinsList) {
            bowling.ballThrow(knockedPins);
        }
    }

    private Object[] getPinsTestData() {
        return new Object[]{
                new Object[]{Arrays.asList(0, 5), 10},
                new Object[]{Arrays.asList(10), 10},
                new Object[]{Arrays.asList(5, 2), 10},
                new Object[]{Arrays.asList(0, 10), 10},
                new Object[]{Arrays.asList(7), 3},
        };
    }

    @Test
    @Parameters(method = "getPinsTestData")
    public void shouldProperlySetNumberOfPinsOnLane(List<Integer> knockedPinsList, int expectedPinsOnLane) {
        for (Integer knockedPins : knockedPinsList) {
            bowling.ballThrow(knockedPins);
        }

        assertEquals(expectedPinsOnLane, bowling.getPinsOnLane());
    }


    private Object[] getThrows() {
        return new Object[]{
                new Object[]{Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), 10},
                new Object[]{Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10, 10), 10},
                new Object[]{Arrays.asList(2, 5, 4, 1, 2, 4, 5, 4), 4},
                new Object[]{Arrays.asList(4, 2, 10, 10, 0, 5), 4},
        };
    }

    @Test
    @Parameters(method = "getThrows")
    public void shouldProperlySetRounds(List<Integer> knockedPinsList, int expectedRounds) {
        for (Integer knockedPins : knockedPinsList) {
            bowling.ballThrow(knockedPins);
        }

        assertEquals(expectedRounds, bowling.getRound());
    }

    private Object[] getToManyRounds() {
        return new Object[]{
                new Object[]{Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0)},
                new Object[]{Arrays.asList(10,10,10,10,10,10,10,10,10,10,10,10,10,10)},
        };
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "getToManyRounds")
    public void shouldSetFixedRoundsNumber(List<Integer> knockedPinsList){
        for (Integer knockedPins : knockedPinsList) {
            bowling.ballThrow(knockedPins);
        }
    }

    private Object[] getStrikeThrows() {
        return new Object[]{
                new Object[]{Arrays.asList(10,0,0), 10},
                new Object[]{Arrays.asList(10,2,5), 17},
                new Object[]{Arrays.asList(10, 10,10), 30},
                new Object[]{Arrays.asList(10, 10,5,2), 25},
                new Object[]{Arrays.asList(10, 5,5), 20},
                new Object[]{Arrays.asList(10, 3,0), 13},
        };
    }

    @Test
    @Parameters(method = "getStrikeThrows")
    public void shouldProperlyCountRoundPointsWhenStrike(List<Integer> knockedPinsList,int expectedRoundPoints){
        for (Integer knockedPins : knockedPinsList) {
            bowling.ballThrow(knockedPins);
        }

        assertEquals(expectedRoundPoints, bowling.getRoundPoints(0));
    }

    private Object[] getSpareThrows() {
        return new Object[]{
                new Object[]{Arrays.asList(0,10,0), 10},
                new Object[]{Arrays.asList(0,10,2), 12},
                new Object[]{Arrays.asList(2,8,10), 20},
                new Object[]{Arrays.asList(5,5, 7), 17},
                new Object[]{Arrays.asList(9,1, 0), 10},
        };
    }

    @Test
    @Parameters(method = "getSpareThrows")
    public void shouldProperlyCountRoundPointsWhenSpare(List<Integer> knockedPinsList,int expectedRoundPoints){
        for (Integer knockedPins : knockedPinsList) {
            bowling.ballThrow(knockedPins);
        }

        assertEquals(expectedRoundPoints, bowling.getRoundPoints(0));
    }


    private Object[] getFinalStrikeThrows() {
        return new Object[]{
                new Object[]{Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10,10),30},
                new Object[]{Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 2,5),17},
                new Object[]{Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10,5),25},
                new Object[]{Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0,0),10},
                new Object[]{Arrays.asList(10,10,10,10,10,10,10,10,10,10,10,0),20},
                new Object[]{Arrays.asList(10,10,10,10,10,10,10,10,10,10,5,0),15},
                new Object[]{Arrays.asList(10,10,10,10,10,10,10,10,10,10,0,0),10},
        };
    }

    @Test
    @Parameters(method = "getFinalStrikeThrows")
    public void shouldGiveAdditionalThrowsWhenStrikeInLastRound(List<Integer> knockedPinsList,int expectedRoundPoints){
        for (Integer knockedPins : knockedPinsList) {
            bowling.ballThrow(knockedPins);
        }

        assertEquals(expectedRoundPoints, bowling.getRoundPoints(9));
    }

    private Object[] getGameThrows() {
        return new Object[]{
                new Object[]{Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), 0},
                new Object[]{Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10,10), 300},
                new Object[]{Arrays.asList(1,1,10,3,7,4,4,0,0,3,7,2,0,4,1,7,2,4,6,2),84},
        };
    }

    @Test
    @Parameters(method = "getGameThrows")
    public void shouldProperlyCountGameScore(List<Integer> knockedPinsList,int expectedGameScore){
        for (Integer knockedPins : knockedPinsList) {
            bowling.ballThrow(knockedPins);
        }

        int gameScore = bowling.getFinalScore();

        assertEquals(expectedGameScore,gameScore);

    }

}