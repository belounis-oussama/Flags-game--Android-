package ca.collegelacite.es03_p22;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/***
 *
 Nom Etudiant :  Anas Hilali
 Groupe:         30
 Evaluation:     ES03
 Session:        Printemps 2022
 *
 ***/
public class MainActivity extends AppCompatActivity {

    static private final String nomFichier = "pays.json";
    GridView gridview;
    TextView tentatives, pays;
    ConstraintLayout constraintlayout;
    Boolean gagner;
    Pays monPays;
    int numTentative;

    // GridView <---> adaptateur <---> listePays

    // Adaptateur faisant le pont entre GridView et les données.

    // Fonction du cycle de vie exécutée lors de la création de l'activité.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gagner = false;
        numTentative = 0;

        gridview = findViewById(R.id.gridview);
        tentatives = findViewById(R.id.tentatives);
        pays = findViewById(R.id.pays);
        constraintlayout = findViewById(R.id.constraintlayout);

        constraintlayout.setBackgroundColor(Color.GRAY);

        // Données source de l'adaptateur
        ArrayList<Pays> listePays = Pays.lireFichier(this, nomFichier);
        if (listePays != null) Collections.shuffle(listePays, new Random());

        int numAleatoire = new Random().nextInt(listePays.size());
        monPays = listePays.get(numAleatoire);
        String nomPays = monPays.toString();
        pays.setText(nomPays);  //nom de pays aleatoire

        //GridView Adapter pour le gridView
        Adapter adapter = new Adapter(MainActivity.this, listePays);
        gridview.setAdapter(adapter);

        //action on long click
        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                String lienWIKI = listePays.get(position).getWikiUrl();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(lienWIKI)));
                return false;
            }
        });

        //action on click simple
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (!gagner) {
                    numTentative = numTentative + 1;
                    tentatives.setText(String.valueOf(numTentative));
                    Pays currentPays = listePays.get(position);
                    currentPays.setDévoilé(true);
                    adapter.notifyDataSetChanged();
                    if (currentPays.equals(monPays)) {
                        //si le joueur a ganger
                        constraintlayout.setBackgroundColor(Color.CYAN);
                        gagner = true;
                        Toast.makeText(MainActivity.this, "Nombre de tentatives " + String.valueOf(numTentative), Toast.LENGTH_SHORT).show();

                    } else {
                        //la reponse est mauvaise
                        constraintlayout.setBackgroundColor(Color.MAGENTA);
                    }
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.recommencer) {
            this.recreate();  //recommencer
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}