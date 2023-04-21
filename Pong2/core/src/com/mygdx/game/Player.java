package com.mygdx.game;

import com.mygdx.game.text.Printout;

import java.lang.management.PlatformLoggingMXBean;

public class Player {
    // VARIABLES
    private int score;
    private String playerName;
    private Printout scoreText;

    // Constructor
    public Player(String name, int xCoord){
        score = 0;
        playerName = name;
        scoreText = new Printout("", xCoord, PongGame.SCREEN_HEIGHT - 25);
        scoreText.text = playerName + ": " + score;
    }

    // Getters and Setters
    public int getScore() {
        return score;
    }
    public String getPlayerName() {
        return playerName;
    }

    public Printout getScoreText() {
        return scoreText;
    }

    public void scoredPoint() {
        // Increment the score, and update the printout
        score++;
        scoreText.text = playerName + ": " + score;
    }

}
