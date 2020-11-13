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

        arrayMatch.add(new Match(name, score, time));
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
                if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    score = Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent());
                    time = Integer.parseInt(eElement.getElementsByTagName("time").item(0).getTextContent());
                    Match m = new Match(name, score, time);
                    arrayMatch.add(m);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Exception while executing readMatchs()");
        }
    }

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
                eMatch.appendChild(eTime
                );
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
}