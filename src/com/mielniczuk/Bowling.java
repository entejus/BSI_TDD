package com.mielniczuk;

import java.util.Arrays;

public class Bowling {
    private int round;
    private int pinsOnLane;
    private int throwNumber;
    private int[] roundPoints;
    private Boolean[] strikeInRound;
    private Boolean[] spareInRound;
    private Boolean additionalThrowsDone;

    public Bowling() {
        this.round = 0;
        this.throwNumber = 0;
        this.pinsOnLane = 10;
        this.roundPoints = new int[10];
        this.strikeInRound = new Boolean[10];
        Arrays.fill(strikeInRound, false);
        this.spareInRound = new Boolean[10];
        Arrays.fill(spareInRound, false);
        this.additionalThrowsDone = false;
    }

    public int getRound() {
        return round;
    }

    public int getPinsOnLane() {
        return pinsOnLane;
    }



    public int getRoundPoints(int roundNumber) {
        return roundPoints[roundNumber];
    }

    public void ballThrow(int knockedPins) {
        if (knockedPins < 0 || knockedPins > 10) {
            throw new IllegalArgumentException("Nieprawidłowa liczb strąconych kręgli");
        } else if (round > 9 && ((!strikeInRound[9] && !spareInRound[9]) || additionalThrowsDone)) {
            throw new IllegalArgumentException("Rozegrano 10 rund");
        } else {
            switch (throwNumber) {
                case 0:
                    //Strike and spare
                    if (round > 0) {
                        if (strikeInRound[round - 1]) {
                            roundPoints[round - 1] += knockedPins;
                            if (round > 1 && strikeInRound[round - 2]) {
                                roundPoints[round - 2] += knockedPins;
                            }
                        }
                        if (spareInRound[round - 1]) {
                            roundPoints[round - 1] += knockedPins;
                            if (round == 10) {
                                additionalThrowsDone = true;
                            }

                        }
                    }

                    //Normal round points
                    if (round < 10) {
                        roundPoints[round] = knockedPins;

                        if (knockedPins == 10) {
                            strikeInRound[round] = true;
                            pinsOnLane = 10;
                            throwNumber = 0;
                            round++;
                        } else {
                            pinsOnLane -= knockedPins;
                            throwNumber = 1;
                        }
                    } else {
                        throwNumber = 1;
                    }
                    break;
                case 1:
                    if (pinsOnLane < knockedPins) {
                        throw new IllegalArgumentException("Nieprawidłowa liczb strąconych kręgli");
                    }

                    if (round > 0 && strikeInRound[round - 1]) {
                        roundPoints[round - 1] += knockedPins;
                        if (round == 10) {
                            additionalThrowsDone = true;
                        }
                    }

                    if (round < 10) {
                        roundPoints[round] = roundPoints[round] + knockedPins;
                        if (roundPoints[round] == 10) {
                            spareInRound[round] = true;
                        }

                        pinsOnLane = 10;
                        round++;
                    }
                    throwNumber = 0;
                    break;
            }


        }

    }

    public int getFinalScore() {
        int score=0;
        for(int i=0;i<roundPoints.length;i++){
            score += roundPoints[i];
        }
        return score;
    }
}
