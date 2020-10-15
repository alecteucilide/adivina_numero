package com.example.adivina_numero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity_Ranking extends AppCompatActivity {
    static ArrayList<String> arrayNames = new ArrayList<String>();
    String name = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__ranking);
        TextView tvRanking = findViewById(R.id.tvRanking);
        arrayNames.add(getIntent().getStringExtra("name"));

        printRanking(tvRanking);


        final Button buttonReturn = findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMainActivity();
            }
        });

    }

    public void callMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void printRanking(TextView tvRanking){
        String names = new String();
        for (String n : arrayNames){
            names = names+"\n"+n;
        }
        tvRanking.setText(names);
    }
}