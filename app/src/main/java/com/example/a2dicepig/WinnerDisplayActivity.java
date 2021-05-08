package com.example.a2dicepig;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The 2DicePig program is an Android game application that
 * allows a user to play 2dice pig the dice game alone
 * or with a friend.
 *
 * This class WinnerDisplay creates an object that controls the activity
 * and visual simulation of the winner display page
 *
 * @author  Kwinn Danforth
 * @version 1.1.01
 */
public class WinnerDisplayActivity extends AppCompatActivity {
    String winner;
    TextView winnerTextView;
    MediaPlayer mediaPlayerPigFarm;

    /**
     * This method is called when the activity_winner_display intent is sent. It sets the content view
     * to activity_winner_display. As well as acts as a constructor to set up things for the WinnerDisplayActivity Class.
     * it also sets the text view to show a congrats to the winner as well as plays the pigfarm.MP3
     *
     * @param savedInstanceState This is the parameter to the onCreate method
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_winner);
        winner = getIntent().getStringExtra("winner");
        winnerTextView = (TextView) findViewById(R.id.winner);
        mediaPlayerPigFarm = MediaPlayer.create(this, R.raw.pig_farm);
        winnerTextView.setText(winner);
        mediaPlayerPigFarm.start();
    }
}
