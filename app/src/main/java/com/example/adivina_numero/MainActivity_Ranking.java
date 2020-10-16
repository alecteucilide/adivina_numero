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
    static ArrayList<Integer> arrayScores = new ArrayList<Integer>();
    static ArrayList<Integer> arrayTimeScores = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__ranking);
        TextView tvRanking = findViewById(R.id.tvRanking);

        String name = getIntent().getStringExtra("name");
        if(name.equals("") || name == null){
            arrayNames.add("Unknown");
        }else{
            arrayNames.add(name);
        }

        arrayScores.add(getIntent().getIntExtra("score", 0));

        arrayTimeScores.add(getIntent().getIntExtra("timeScore", 0));

        orderArrays(arrayNames, arrayScores, arrayTimeScores);
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
        String scores = new String();
        for (int i = 0; i<arrayNames.size();i++){
            scores = scores+"\n"+arrayNames.get(i)+"          "+arrayScores.get(i)+"       "+arrayTimeScores.get(i);
        }
        tvRanking.setText(scores);
    }

    public void orderArrays(ArrayList<String> arrayNames, ArrayList<Integer> arrayScores, ArrayList<Integer> arrayTimeScores){
        for(int i = arrayScores.size()-1; i>=1; i--){
            for(int j = 0; j < i; j++){
                if(arrayScores.get(j)>arrayScores.get(i)){
                    int numChange = arrayScores.get(j);
                    arrayScores.set(j, arrayScores.get(i));
                    arrayScores.set(i, numChange);
                    String nameChange = arrayNames.get(j);
                    arrayNames.set(j, arrayNames.get(i));
                    arrayNames.set(i, nameChange);
                    int timeChange = arrayTimeScores.get(j);
                    arrayTimeScores.set(j, arrayTimeScores.get(i));
                    arrayTimeScores.set(i, timeChange);
                }
            }
        }
    }
}