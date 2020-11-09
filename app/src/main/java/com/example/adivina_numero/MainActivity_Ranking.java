package com.example.adivina_numero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity_Ranking extends AppCompatActivity {
    static ArrayList<Match> arrayMatch = new ArrayList<Match>();
    RecyclerView rvRanking;
    RecyclerAdapter recyclerAdapter;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__ranking);
        dispatchTakePictureIntent();
        RecyclerView rvRanking = findViewById(R.id.rvRanking);
        recyclerAdapter = new RecyclerAdapter(arrayMatch, imageBitmap);
        rvRanking.setLayoutManager(new LinearLayoutManager(this));
        rvRanking.setAdapter(recyclerAdapter);

        String name = getIntent().getStringExtra("name");
        if(name.equals("") || name == null){
            name = "Unknown";
        }
        int score = getIntent().getIntExtra("score", 0);
        int time = getIntent().getIntExtra("timeScore", 0);

        arrayMatch.add(new Match(name, score, time));
        Collections.sort(arrayMatch);

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
        for (Match m : arrayMatch){
           addRowToTable(tlRanking, m.getName(), m.getScore(), m.getTime());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            //ivPhoto.setImageBitmap(imageBitmap);
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            Toast.makeText(MainActivity_Ranking.this, "No camera found.", Toast.LENGTH_SHORT).show();
        }
    }
}