package com.example.a2dicepig;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class GameDataFileCreator extends AppCompatActivity {
    private String MY_FILE_NAME = "game_data.csv";


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getGameData();
    }


    private void getGameData()
    {
        InputStream is = getResources().openRawResource(R.raw.game_data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            FileOutputStream fileOut = openFileOutput(MY_FILE_NAME, MODE_PRIVATE);
            OutputStreamWriter outputWriter = null;
            outputWriter = new OutputStreamWriter(fileOut);
            while ((line = reader.readLine()) != null) {
                //Split by comma
                String[] tokens = line.split(",");

                //write data to new file
                try {
                    outputWriter.append(tokens[0] + "," + tokens[1] + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();
            outputWriter.close();
            fileOut.close();
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading Data File on line: " + line, e);
            e.printStackTrace();
        }
        Intent intent = new Intent(GameDataFileCreator.this, MainActivity.class);
        startActivity(intent);
    }
}
