package com.example.a2dicepig;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

/**
 * The 2DicePig program is an Android game application that
 * allows a user to play 2dice pig the dice game alone
 * or with a friend.
 *
 * This class RollActivity creates an object that controls the activity
 * and visual simulation of rolling the two dice
 *
 * @author  Kwinn Danforth
 * @version 1.1.01
 */
public class RollActivity extends AppCompatActivity {
    private static ImageView imgView;
    private static ImageView imgViewTwo;
    private static Button rollButton;
    private static Button holdButton;
    private boolean Player2isCPU;
    private MediaPlayer mediaPlayerPigPissd;
    private int current_side;
    private int die1Side;
    private int die2Side;
    int[] sides = {R.drawable.die1, R.drawable.die2, R.drawable.die3, R.drawable.die4, R.drawable.die5, R.drawable.die6};

    /**
     * This method is called when the activity_roll intent is sent.
     * It sets the contentView to activity_roll
     * It dose some of the set up for our class and calls the methods
     * that start the click listener functions.
     *
     * @param savedInstanceState This is the parameter to the onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);
        Player2isCPU = getIntent().getBooleanExtra("cpuPlayer2", false);
        mediaPlayerPigPissd = MediaPlayer.create(this, R.raw.pig_pissd);
        rollButtonClick();
        holdButtonClick();
        if(Player2isCPU) {
            cPURoll();
        }
    }

    /**
     * This method is called if the player2=CPU switch is on
     * It simulates the CPU player2 pushing the button to roll the dice
     *
     */
    private void cPURoll() {
        rollButton.performClick();
    }

    /**
     * This method sets up the click listener for the roll button
     * Inside it calls the onClick method if the roll button is clicked
     * when it is clicked onClick simulated=s rolling two dice
     *
     */
    public void rollButtonClick()
    {
        imgView = (ImageView) findViewById(R.id.dieView);
        imgViewTwo = (ImageView) findViewById(R.id.dieView2);
        rollButton = (Button) findViewById(R.id.rollButton);
        holdButton = (Button) findViewById(R.id.holdButton);
        rollButton.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          current_side = new Random().nextInt(6);
                                          imgView.setImageResource(sides[current_side]);
                                          die1Side = (current_side + 1);
                                          current_side = new Random().nextInt(6);
                                          imgViewTwo.setImageResource(sides[current_side]);
                                          die2Side = (current_side + 1);
                                          if(die1Side == 1 && die2Side == 1){
                                              mediaPlayerPigPissd.start();
                                          }
                                          goToScorePage();
                                      }
                                    }
                                    );

    }

    /**
     * This method sets up the click listener for the hold button.
     * Inside it calls the onClick method, if the hold button is clicked.
     * When it is clicked onClick starts the user back to the home page without
     * rolling the dice. It also sends some information to goToScore() to replace the die1Side and
     * die2Side. This info is used so that no points are scored on the hold
     *
     */
    public void holdButtonClick() {
        holdButton = (Button) findViewById(R.id.holdButton);
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScorePage(true);
            }
        });
    }

    /**
     * This method sends the user back to the activity_main "score" page.
     * it adds in the Extras including the two sides of the die that where
     * rolled and sends them to the MainActivity.
     *
     */
    private void goToScorePage() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(RollActivity.this, MainActivity.class);
                intent.putExtra("die1Side", die1Side );
                intent.putExtra("die2Side", die2Side );
                startActivity(intent);
            }
        }, 2000);
    }

    /**
     * This method sends the user back to the activity_main "score" page.
     * it adds in the Extras including the numbers that take place of die1Side and
     * die2Side in the case of a hold and sends them with the intent to the MainActivity.
     *
     * @param hold, boolean weather or not this is a hold
     *
     */
    private void goToScorePage(boolean hold) {
        Intent intent = new Intent(RollActivity.this, MainActivity.class);
        intent.putExtra("die1Side", 1 );
        intent.putExtra("die2Side", 0 );
        startActivity(intent);
    }
}