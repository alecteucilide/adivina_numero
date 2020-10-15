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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonName = findViewById(R.id.buttonName);
        buttonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText enteredName = findViewById(R.id.textInPutName);
                name = enteredName.getText().toString();
                Toast.makeText(MainActivity.this, "Name registered.", Toast.LENGTH_SHORT).show();
            }
        });

        final Button buttonRanking = findViewById(R.id.buttonRanking);
        buttonRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(name == null) && !name.equals("")){
                    //Toast.makeText(MainActivity.this, "Your name is "+name, Toast.LENGTH_SHORT).show();
                    callRanking();
                }else{
                    Toast.makeText(MainActivity.this, "Name not valid."+name, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void callRanking(){
        Intent intent = new Intent(this, MainActivity_Ranking.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}