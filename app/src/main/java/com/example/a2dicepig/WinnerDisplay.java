package com.example.a2dicepig;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WinnerDisplay extends AppCompatActivity {
    String winner;
    TextView winnerTextView;
    MediaPlayer mediaPlayerPigFarm;
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
