package com.example.a2dicepig;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The 2DicePig program is an Android game application that
 * allows a user to play 2dice pig the dice game alone
 * or with a friend.
 *
 * This class RulesActivity creates an object that controls the activity
 * and visual simulation of the rules page
 *
 * @author  Kwinn Danforth
 * @version 1.1.01
 */
public class RulesActivity extends AppCompatActivity {

    /**
     * This method is called when the activity_rules intent is sent. It sets the content view
     * to activity_rules.
     *
     * @param savedInstanceState This is the parameter to the onCreate method
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
    }
}
