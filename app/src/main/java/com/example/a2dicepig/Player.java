package com.example.a2dicepig;

/**
 * The 2DicePig program is an Android game application that
 * allows a user to play 2dice pig the dice game alone
 * or with a friend.
 *
 * This Player class creates an object that we use to represent the players in our game.
 *
 * @author  Kwinn Danforth
 * @version 1.1.01
 */
public class Player {
    private String name;
    private int score;

    /**
     * This is a getter method for the name field of our player
     *
     * @return name, The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * This is a setter method for the name field of our player
     *
     * @param name, The name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This is a getter method for the score field of our player
     *
     * @return score, The score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * This is a setter method for the name field of our player
     *
     * @param score, The score of the player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This is a getter method for the score field of our player but it returns the
     * score as a string instead of an integer.
     *
     * @return score, The score of the player
     */
    public String getScoreAsString() {
        return Integer.toString(score);
    }
}
