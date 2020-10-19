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

        TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        TextView textview = new TextView(this);
        textview.setText("Name");
        tr1.addView(textview);
        tlRanking.addView(tr1, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        //printRanking(tlRanking);

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

    public void printRanking(TableLayout tlRanking){
        for (int i = 0; i<arrayNames.size();i++){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView tvName = new TextView(this);
            tvName.setText(arrayNames.get(i));
            row.addView(tvName);
            TextView tvScore = new TextView(this);
            tvScore.setText(arrayScores.get(i));
            row.addView(tvScore);
            TextView tvTime = new TextView(this);
            tvTime.setText(arrayTimeScores.get(i));
            row.addView(tvTime);

            tlRanking.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
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