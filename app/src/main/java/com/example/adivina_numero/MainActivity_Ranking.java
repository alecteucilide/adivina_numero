package com.example.adivina_numero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
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
        TableLayout tlRanking = (TableLayout) findViewById(R.id.tableRanking);

        String name = getIntent().getStringExtra("name");
        if(name.equals("") || name == null){
            arrayNames.add("Unknown");
        }else{
            arrayNames.add(name);
        }

        arrayScores.add(getIntent().getIntExtra("score", 0));

        arrayTimeScores.add(getIntent().getIntExtra("timeScore", 0));

        orderArrays(arrayNames, arrayScores, arrayTimeScores);

        printRankingIntoTable(tlRanking);

        final Button buttonReturn = findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMainActivity();
            }
        });

    }

    public void addRowToTable(TableLayout tlRanking, String name, Integer score, Integer time){
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        TextView tvName = new TextView(this);
        tvName.setText(name);
        tr.addView(tvName);
        TextView tvScore = new TextView(this);
        tvScore.setText(String.valueOf(score));
        tr.addView(tvScore);
        TextView tvTime = new TextView(this);
        tvTime.setText(String.valueOf(time));
        tr.addView(tvTime);
        tlRanking.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    public void callMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void printRankingIntoTable(TableLayout tlRanking){
        for (int i = 0; i<arrayNames.size();i++){
           addRowToTable(tlRanking, arrayNames.get(i), arrayScores.get(i), arrayTimeScores.get(i));
        }

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