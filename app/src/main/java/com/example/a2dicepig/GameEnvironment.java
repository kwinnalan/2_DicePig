package com.example.a2dicepig;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The 2DicePig program is an Android game application that
 * allows a user to play 2dice pig the dice game alone
 * or with a friend.
 *
 * This class GameEnvironment creates an object that has methods we can call to help score our game.
 * (was planned to move more over here).
 *
 * @author  Kwinn Danforth
 * @version 1.1.01
 */
public class GameEnvironment extends AppCompatActivity {

    /**
     * This method checks to see if two integers are the same. In this game we use that to check
     * and see if the dice simulation returned doubles.
     *
     * @param die1, One of the two integers to check
     * @param die1, One of the two integers to check
     */
    public boolean isDoubles(int die1, int die2) {
        if (die1 == die2) {
            return true;
        } else {
            return false;
        }
    }
}