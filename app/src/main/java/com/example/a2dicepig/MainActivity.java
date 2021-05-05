package com.example.a2dicepig;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity
{
    private int GOAL_SCORE;
    private GameEnvironment game;
    private Button goToRollButton;
    private Button rulesButton;
    private Switch player2CPU;
    private boolean player2isCPU;
    private int die1Side;
    private int die2Side;
    private int totalRoll;
    private int whosTurn;
    private boolean isFirstRound;
    private List<Player> players = new ArrayList<>();
    private TextView player1ScoreView;
    private TextView player2ScoreView;
    private TextView currentPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player2CPU = (Switch) findViewById(R.id.CPU_Player2);
        goToRollButton = (Button) findViewById(R.id.button);
        GOAL_SCORE = 100;
        game = new GameEnvironment();
        die1Side = getIntent().getIntExtra("die1Side", 0);
        die2Side = getIntent().getIntExtra("die2Side", 1);
        totalRoll = (die1Side + die2Side);

        checkSharedPref();
        getGameData();

        if(players.get(0).getScore() < GOAL_SCORE && players.get(1).getScore() < GOAL_SCORE) {
            if (!isFirstRound) {
                scoreRoll(die1Side, die2Side);
            } else {
                changeFirstRoundPrefToFalse();
            }
        }else{
            winnerDisplay();
            newGame();
        }
        displayScore();
        player2CpuSwitch();
        CPUplay();
        goToRollButtonClick();
        rulesButtonClick();
    }

    private void CPUplay() {
        if(player2isCPU && whosTurn == 1){
            if(players.get(0).getScore() >= GOAL_SCORE || players.get(1).getScore() >= GOAL_SCORE){
                winnerDisplay();
                newGame();
            }else {
                Intent intent = new Intent(MainActivity.this, RollActivity.class);
                intent.putExtra("cpuPlayer2", true);
                startActivity(intent);
            }
        }
    }

    private void player2CpuSwitch() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor edit = prefs.edit();
        player2CPU.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    player2isCPU = true;
                    edit.putBoolean(getString(R.string.player_two_cpu_switch), Boolean.TRUE);

                }else{
                    player2isCPU = false;
                    edit.putBoolean(getString(R.string.player_two_cpu_switch), Boolean.FALSE);
                }
                edit.commit();
            }
        });
    }


    private void changeFirstRoundPrefToFalse() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(getString(R.string.pref_is_first_round), Boolean.FALSE);
        edit.commit();
    }

    private void scoreRoll(int die1Side, int die2Side) {
        if(die2Side == 0){                      //meaning hold was pressed
            changeTurn();
        }else if(game.isDoubles(die1Side, die2Side)){
            if (die1Side != 1) {
                players.get(whosTurn).setScore(players.get(whosTurn).getScore() + (2 * totalRoll));
                editRecord(players.get(whosTurn).getName(), players.get(whosTurn).getScoreAsString());
            } else {
                players.get(whosTurn).setScore(0);
                editRecord(players.get(whosTurn).getName(), players.get(whosTurn).getScoreAsString());
                changeTurn();
            }
        }else if(die1Side == 1 || die2Side == 1){
            changeTurn();
        }else {
            players.get(whosTurn).setScore(players.get(whosTurn).getScore() + totalRoll);
            editRecord(players.get(whosTurn).getName(), players.get(whosTurn).getScoreAsString());
        }
    }

    private void getGameData()
    {
        BufferedReader reader;
        try (FileInputStream is = openFileInput("game_data.csv")) {
            reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
        String line = "";
            String[] firstLine = reader.readLine().split(",");
            whosTurn = Integer.parseInt(firstLine[1]);
            while ((line = reader.readLine()) != null) {
                //Split by comma
                String[] tokens = line.split(",");
                //read data
                Player player = new Player();
                player.setName(tokens[0]);
                player.setScore(Integer.parseInt(tokens[1]));
                players.add(player);
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading Data File.");
            e.printStackTrace();
        }
   }

    private void goToRollButtonClick()
    {
        goToRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(players.get(0).getScore() >= GOAL_SCORE || players.get(1).getScore() >= GOAL_SCORE){
                    winnerDisplay();
                    newGame();
                }else {
                    Intent intent = new Intent(MainActivity.this, RollActivity.class);
                    intent.putExtra("cpuPlayer2", false);
                    startActivity(intent);
                }
            }
        });
    }

    public void displayScore()
    {
        player1ScoreView = (TextView) findViewById(R.id.player1Score);
        player2ScoreView = (TextView) findViewById(R.id.player2Score);
        currentPlayer =(TextView) findViewById(R.id.currentPlayer);

        player1ScoreView.append("\n" + players.get(0).getScoreAsString());
        player2ScoreView.append("\n" + players.get(1).getScoreAsString());
        currentPlayer.setText(players.get(whosTurn).getName());
    }

    public void changeTurn() {
        if (whosTurn == 0) {
            editRecord("0", "1");
            whosTurn = 1;
        } else {
            editRecord("0", "0");
            whosTurn = 0;
        }
    }

    public void editRecord(String editTerm, String newScore) {
        try{
            FileOutputStream fileOutputStream = openFileOutput("temp.csv", MODE_PRIVATE);
            OutputStreamWriter outputWriter = null;
            outputWriter = new OutputStreamWriter(fileOutputStream);
            if (editTerm.equals("0")) {
                outputWriter.append((editTerm + "," + newScore + "\n"));
                outputWriter.append(("Player1" + "," + players.get(0).getScoreAsString() + "\n"));
                outputWriter.append(("Player2" + "," + players.get(1).getScoreAsString() + "\n"));
            } else if(editTerm.equals("Player1")){
                outputWriter.append(("0" + "," + whosTurn + "\n"));
                outputWriter.append((editTerm + "," + newScore + "\n"));
                outputWriter.append(("Player2" + "," + players.get(1).getScoreAsString() + "\n"));
            }else{
                outputWriter.append(("0" + "," + whosTurn + "\n"));
                outputWriter.append(("Player1" + "," + players.get(0).getScoreAsString() + "\n"));
                outputWriter.append((editTerm + "," + newScore + "\n"));
            }
            outputWriter.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File oldFile = new File("/data/data/com.example.a2dicepig/files/game_data.csv");
        boolean a = oldFile.delete();
        File tempFile = new File("/data/data/com.example.a2dicepig/files/temp.csv");
        File newFile = new File("/data/data/com.example.a2dicepig/files/game_data.csv");
        boolean b = tempFile.renameTo(newFile);

    }

    public void checkSharedPref() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
            createGameDataFile();
        }
        boolean firstRound = prefs.getBoolean(getString(R.string.pref_is_first_round), true);
        if(firstRound){
            isFirstRound = true;
        }else {
            isFirstRound = false;
        }
        boolean player2CPUswitch = prefs.getBoolean(getString(R.string.player_two_cpu_switch),false);
        if(player2CPUswitch){
            player2CPU.setChecked(true);
            player2isCPU = true;
        }else{
            player2isCPU = false;
        }
    }

    private void winnerDisplay() {
        String winner;
        if(players.get(0).getScore() > players.get(1).getScore()){
            winner = players.get(0).getName();
        }else {
            winner = players.get(1).getName();
        }
        Intent intent = new Intent(MainActivity.this, WinnerDisplay.class);
        intent.putExtra("winner", winner);
        startActivity(intent);
    }

    private void newGame() {
        for(int i = 0; i < players.size(); i++) {
            players.get(i).setScore(0);
            editRecord(players.get(i).getName(), players.get(whosTurn).getScoreAsString());
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(getString(R.string.is_first_round), Boolean.TRUE);
        edit.commit();
    }

    private void createGameDataFile() {
        Intent intent = new Intent(MainActivity.this, GameDataFileCreator.class);
        startActivity(intent);
    }

    private void rulesButtonClick() {
        rulesButton = (Button) findViewById(R.id.rulesButton);
        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRulesPage();
            }
        });
    }

    private void goToRulesPage() {
        Intent intent = new Intent(MainActivity.this, RulesActivity.class);
        startActivity(intent);
    }
}

