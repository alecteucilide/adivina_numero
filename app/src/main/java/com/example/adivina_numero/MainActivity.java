package com.example.adivina_numero;
//hacer u realease v0.1 con tag = vo.1 -> en github
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    String name = new String();
    int numAttempts = 0;
    int score = 0;
    int secretNum = (int)((Math.random()*99)+1);
    int time = 0;
    int timeScore = 0;
    boolean playing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        creatTimer();

        final Button buttonNumber = findViewById(R.id.buttonNumber);
        buttonNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText enteredNum = (EditText) findViewById (R.id.textInPutNumber);
                String enteredNumString = enteredNum.getText().toString();
                playing = true;
                try{
                    int enteredNumInt = Integer.parseInt(enteredNumString);
                    numAttempts++;
                    if(enteredNumInt<secretNum){
                        Toast.makeText(MainActivity.this, "The sercret number is bigger.", Toast.LENGTH_SHORT).show();
                    }else if(enteredNumInt>secretNum) {
                        Toast.makeText(MainActivity.this, "The secret number is smaller.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "You have found the secret number in "+numAttempts+" attempts.", Toast.LENGTH_SHORT).show();
                        restartGame();
                        showDialogue();
                        playing = false;
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Invalid entry.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button buttonRanking = findViewById(R.id.buttonRanking);
        buttonRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score > 0){
                    if(playing==false){
                            callRanking();
                    }else{
                        Toast.makeText(MainActivity.this, "End current game before accessing ranking.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "You have to win to acces ranking.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void restartGame(){
        score = numAttempts;
        timeScore = time;
        numAttempts = 0;
        secretNum = (int)((Math.random()*99)+1);
        time = 0;
    }

    public void callRanking(){
        Intent intent = new Intent(this, MainActivity_Ranking.class);
        intent.putExtra("name", name);
        intent.putExtra("score", score);
        intent.putExtra("timeScore", timeScore);
        startActivity(intent);
    }

    public void creatTimer(){
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                time++;
            }
        };
        t.schedule(tt, 0, 1000);
    }

    public void showDialogue(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Name dialogue");
        alert.setMessage("Enter your name");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                name = input.getText().toString();
                if(name.equals("") || name == null){
                    Toast.makeText(MainActivity.this, "Invalid name.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
}