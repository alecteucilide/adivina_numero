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
    static ArrayList<Integer> arrayNumAttempts = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__ranking);
        TextView tvRanking = findViewById(R.id.tvRanking);
        arrayNames.add(getIntent().getStringExtra("name"));
        arrayNumAttempts.add(getIntent().getIntExtra("numAttempts", 0));

        orderArrays(arrayNames, arrayNumAttempts);
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
            scores = scores+"\n"+arrayNames.get(i)+"          "+arrayNumAttempts.get(i);
        }
        tvRanking.setText(scores);
    }

    public void orderArrays(ArrayList<String> arrayNames, ArrayList<Integer> arrayNumAttempts){
        for(int i = arrayNumAttempts.size()-1; i>=1; i--){
            for(int j = 0; j < i; j++){
                if(arrayNumAttempts.get(j)>arrayNumAttempts.get(i)){
                    int numChange = arrayNumAttempts.get(j);
                    arrayNumAttempts.set(j, arrayNumAttempts.get(i));
                    arrayNumAttempts.set(i, numChange);
                    String nameChange = arrayNames.get(j);
                    arrayNames.set(j, arrayNames.get(i));
                    arrayNames.set(i, nameChange);
                }
            }
        }
    }
}