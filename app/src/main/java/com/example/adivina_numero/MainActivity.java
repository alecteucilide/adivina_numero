package com.example.adivina_numero;
//hacer u realease v0.1 con tag = vo.1 -> en github
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
/*onRsultActivty
*
* dos maneras de guardar en el disco duro -> perisintencia -> dcim o memoria interna de la app */
public class MainActivity extends AppCompatActivity {
    String name = new String();
    int numAttempts = 0;
    int score = 0;
    int secretNum = (int)((Math.random()*100)+1);
    int time = 0;
    int timeScore = 0;
    boolean playing = true;
    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;

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
                        enteredNum.setText("");
                    }else if(enteredNumInt>secretNum) {
                        Toast.makeText(MainActivity.this, "The secret number is smaller.", Toast.LENGTH_SHORT).show();
                        enteredNum.setText("");
                    }else{
                        Toast.makeText(MainActivity.this, "You have found the secret number in "+numAttempts+" attempts.", Toast.LENGTH_SHORT).show();
                        restartGame();
                        showDialogue();
                        dispatchTakePictureIntent();
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
        secretNum = (int)((Math.random()*100)+1);
        time = 0;
    }

    /**
     * Metodo para generar un intent y pasar los datos de la pratida a la nueva activity generada.
     */
    public void callRanking(){
        Intent intent = new Intent(this, MainActivity_Ranking.class);
        intent.putExtra("name", name);
        intent.putExtra("score", score);
        intent.putExtra("timeScore", timeScore);
        intent.putExtra("photoPath", currentPhotoPath);
        startActivity(intent);
    }

    /**
     * Metodo para crear un "chronometro".
     */
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

    /**
     * Metodo para permitir entrar al ususario su nombre al acabar la partida.
     * Verifica que el nombre entrado no sea nulo.
     */
    public void showDialogue(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Enter your name : ");
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

    /**
     * Metodo que permite generar un intent para tomar una foto y guardarla.
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * Metodo que permite generar un objeto file asociado a la imagen por dispatchTakePictureIntent().
     * @return File image
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}