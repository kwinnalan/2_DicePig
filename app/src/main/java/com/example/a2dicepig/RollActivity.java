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

    private void cPURoll() {
        rollButton.performClick();
    }

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

    public void holdButtonClick() {
        holdButton = (Button) findViewById(R.id.holdButton);
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScorePage(true);
            }
        });
    }


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

    private void goToScorePage(boolean hold) {
        Intent intent = new Intent(RollActivity.this, MainActivity.class);
        intent.putExtra("die1Side", 1 );
        intent.putExtra("die2Side", 0 );
        startActivity(intent);
    }
}