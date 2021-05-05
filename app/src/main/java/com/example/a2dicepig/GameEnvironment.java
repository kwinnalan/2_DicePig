package com.example.a2dicepig;

import androidx.appcompat.app.AppCompatActivity;

public class GameEnvironment extends AppCompatActivity {



    public boolean isDoubles(int die1, int die2) {
        if (die1 == die2) {
            return true;
        } else {
            return false;
        }
    }



}