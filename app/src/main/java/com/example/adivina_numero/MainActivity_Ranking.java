package com.example.adivina_numero;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity_Ranking extends AppCompatActivity {
    static ArrayList<Match> arrayMatch = new ArrayList<Match>();
    RecyclerView rvRanking;
    RecyclerAdapter recyclerAdapter;

    /**
     * Estrategia de flujo de datos en MainActivity_Ranking
     *
     * Al iniciar la activity se crea un xml (matchs.xml) para guardar los datos si no existe. Se borra el contenido
     * del arrayMatch y se leen y guardan los datos de matchs.xml en ese array. Se recuperan los datos del MainActivity
     * atraves del intent con los cuales se crea una instancia de Match que se guarda en arrayMatch. Se ordena arrayMatch
     * segun el score y time. Se destruye matchs.xml y se vuelve a crear un xml con el mismo nombre guardando la informacion
     * de todos los objetos contenidos en arrayMatch.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__ranking);

        createFile();

        readMatchs();

        RecyclerView rvRanking = findViewById(R.id.rvRanking);
        recyclerAdapter = new RecyclerAdapter(arrayMatch);
        rvRanking.setLayoutManager(new LinearLayoutManager(this));
        rvRanking.setAdapter(recyclerAdapter);

        String name = getIntent().getStringExtra("name");
        if(name.equals("") || name == null){
            name = "Unknown";
        }
        int score = getIntent().getIntExtra("score", 0);
        int time = getIntent().getIntExtra("timeScore", 0);
        String photoPath = getIntent().getStringExtra("photoPath");

        arrayMatch.add(new Match(name, score, time, photoPath));
        Collections.sort(arrayMatch);

        saveMatchs();

        final Button buttonReturn = findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMainActivity();
            }
        });

    }

    /**
     * Metodo para crear una "base de datos" vacia cuando la applicaion se ejecuta por primera vez.
     */
    public void createFile(){
        File path = getFilesDir();
        File file = new File(path.getPath()+"/matchs.xml");

        if(!file.exists()){
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.newDocument();

                // definimos el elemento raíz del documento
                Element eRaiz = doc.createElement("matchs");
                doc.appendChild(eRaiz);

                // clases necesarias finalizar la creación del archivo XML
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(String.valueOf(file)));

                transformer.transform(source, result);
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Exception while executing createFile()");
            }
        }
    }

    /**
     * Metodo para guardar todas las partidas (Match) guardadas en matchs.xml en el array arrayMatch.
     */
    public void readMatchs() {
        File path = getFilesDir();
        File file = new File(path.getPath()+"/matchs.xml");
        System.out.println(String.valueOf(String.valueOf(file.getAbsolutePath())));

        arrayMatch.clear();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("match");

            for(int index = 0; index < nList.getLength(); index++) {
                Node nNode = nList.item(index);
                String name;
                int score;
                int time;
                String photoPath;
                if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    score = Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent());
                    time = Integer.parseInt(eElement.getElementsByTagName("time").item(0).getTextContent());
                    photoPath = eElement.getElementsByTagName("photoPath").item(0).getTextContent();
                    Match m = new Match(name, score, time, photoPath);
                    arrayMatch.add(m);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Exception while executing readMatchs()");
        }
    }

    /**
     * Metodo para guardar las partidas (Match) del arrayMatch en matchs.xml
     */
    public void saveMatchs(){
        File path = getFilesDir();
        File file = new File(path.getPath()+"/matchs.xml");
        file.delete();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            // definimos el elemento raíz del documento
            Element eRaiz = doc.createElement("matchs");
            doc.appendChild(eRaiz);

            for(Match m: arrayMatch){
                Element eMatch = doc.createElement("match");
                eRaiz.appendChild(eMatch);

                Element eName = doc.createElement("name");
                eName.appendChild(doc.createTextNode(m.getName()));
                eMatch.appendChild(eName);

                Element eScore = doc.createElement("score");
                eScore.appendChild(doc.createTextNode(String.valueOf(m.getScore())));
                eMatch.appendChild(eScore);

                Element eTime = doc.createElement("time");
                eTime.appendChild(doc.createTextNode(String.valueOf(m.getTime())));
                eMatch.appendChild(eTime);

                Element ePhotoPath = doc.createElement("photoPath");
                ePhotoPath.appendChild(doc.createTextNode(m.getPhotoPath()));
                eMatch.appendChild(ePhotoPath);
            }

            // clases necesarias finalizar la creación del archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(String.valueOf(file)));

            transformer.transform(source, result);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Exception while executing saveMatchs()");
        }
    }

    /**
     * Metodo para regresar a la pagina de juego (MainActivity) desde la pagina ranking (esta activity)
     */
    public void callMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}