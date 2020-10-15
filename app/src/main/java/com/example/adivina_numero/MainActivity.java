package com.example.adivina_numero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String name = new String();
    int numAttempts = 0;
    int secretNum = 0;
    boolean playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonNumber = findViewById(R.id.buttonNumber);
        EditText enteredNumber = findViewById(R.id.textInPutNumber);

        final Button buttonName = findViewById(R.id.buttonName);
        buttonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText enteredName = findViewById(R.id.textInPutName);
                name = enteredName.getText().toString();
                if(name.equals("") || name == null){
                    Toast.makeText(MainActivity.this, "Invalid name.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Name registered.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button buttonRanking = findViewById(R.id.buttonRanking);
        buttonRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numAttempts > 0){
                    if(playing==false){
                        if(!(name == null) && !name.equals("")){
                            //Toast.makeText(MainActivity.this, "Your name is "+name, Toast.LENGTH_SHORT).show();
                            callRanking();
                        }else{
                            Toast.makeText(MainActivity.this, "Name not valid."+name, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "End current game before accessing ranking."+name, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "You have to play at least once to acces ranking."+name, Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Game started!", Toast.LENGTH_SHORT).show();
                numAttempts=0;
                name="";
                secretNum = (int)(Math.random()*1)+1;
                play(buttonNumber, enteredNumber, secretNum);
            }
        });
    }

    public void callRanking(){
        Intent intent = new Intent(this, MainActivity_Ranking.class);
        intent.putExtra("name", name);
        intent.putExtra("numAttempts", numAttempts);
        startActivity(intent);
    }

    public void play(Button buttonNumber, EditText enteredNumber, int secretNumber){
        buttonNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playing = true;
                EditText enteredNum = (EditText) findViewById (R.id.textInPutNumber);
                String enteredNumString = enteredNum.getText().toString();

                try{
                    int enteredNumInt = Integer.parseInt(enteredNumString);
                    numAttempts++;
                    if(enteredNumInt<secretNum){
                        Toast.makeText(MainActivity.this, "The sercret number is bigger.", Toast.LENGTH_SHORT).show();
                    }else if(enteredNumInt>secretNum) {
                        Toast.makeText(MainActivity.this, "The secret number is smaller.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "You have found the secret number in "+numAttempts+" attempts.", Toast.LENGTH_SHORT).show();
                        playing = false;
                        return;
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Invalid entry.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}