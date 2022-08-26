package ca.collegelacite.es03_p22;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

// Classe implantant un descriptif de pays
public class Pays implements Comparable, Serializable {
    private String code;          // code ISO du pays
    private String nom;           // nom du pays
    private String wikiUrl;       // url vers wikipédia
    private boolean dévoilé  = false;   // indique si le nom doit être affiché

    private static Context contexte = null;   // requis pour lire le JSON

    // Constructeur par défaut.
    public Pays() {
        this.setCode(null);
        this.setNom(null);
        this.setWikiUrl(null);
        this.dévoilé  = false;
    }

    // Constructeur paramétré.
    public Pays(String code, String nom, String wikiUrl, boolean dévoilé) {
        this.setCode(code);
        this.setNom(nom);
        this.setWikiUrl(wikiUrl);
        this.dévoilé  = dévoilé;
    }

    // Fonction de comparaison utilisée pour trier la liste de pays après la
    // lecture du JSON.
    @Override
    public int compareTo(Object autrePays) {
        return this.toString().compareTo(autrePays.toString());
    }

    // Accesseur de l'attribut code.
    public String getCode() {
        return code;
    }

    // Mutateur de l'attribut code.
    public void setCode(String code) {
        this.code = code;
    }

    // Accesseur de l'attribut nom. À noter que l'attribut dévoilé doit
    // être vrai pour que le nom soit retourné.
    public String getNom() {
        if (this.dévoilé)
            return nom;
        else
            return null;
    }

    // Mutateur de l'attribut nom.
    public void setNom(String nom) {
        this.nom = nom;
    }


    // Accesseur de l'attribut wiki.
    public String getWikiUrl() {
        return wikiUrl;
    }

    // Mutateur de l'attribut wiki.
    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    // Accesseur de l'attribut dévoilé
    public boolean isDévoilé() {
        return dévoilé;
    }

    // Mutateur de l'attribut dévoilé
    public void setDévoilé(boolean dévoilé) {
        this.dévoilé = dévoilé;
    }

    // Fonction permettant d'insérer l'image du drapeau du pays dans un
    // ImageView fourni. L'image du drapeau doit être dans res/drawable et porter
    // un nom correspondant au code du pays.
    public void drapeauDansImageView(ImageView iv) {
        String uri = "@drawable/" + this.getCode().toLowerCase();

        int imageResource = contexte.getResources().getIdentifier(uri, null, contexte.getPackageName());

        Drawable res = contexte.getDrawable(imageResource);
        iv.setImageDrawable(res);
    }

    // Retourne une chaîne décrivant le pays.
    @Override
    public String toString() {
        return this.nom;
    }

    // Désérialiser une liste de pays d'un fichier JSON.
    public static ArrayList<Pays> lireFichier(Context ctx, String nomFichier) {
        // Sauvegarder le contexte pour la gestion des drawables.
        contexte = ctx;

        ArrayList<Pays> liste = new ArrayList<>();
        try {
            // Charger les données dans la liste.
            String jsonString = lireJson(nomFichier);
            JSONObject json   = new JSONObject(jsonString);
            JSONArray pays    = json.getJSONArray("pays");

            // Lire chaque pays du fichier.
            for(int i = 0; i < pays.length() ; i++){
                Pays p = new Pays();

                p.code    = pays.getJSONObject(i).getString("code");
                p.nom     = pays.getJSONObject(i).getString("nom");
                p.wikiUrl = pays.getJSONObject(i).getString("wiki");

                liste.add(p);
            }
        } catch (JSONException e) {
            // Une erreur s'est produite (on la journalise).
            e.printStackTrace();
            return null;
        }

        return liste;
    }

    // Retourne une balise lue d'un fichier JSON.
    private static String lireJson(String nomFichier) {
        String json = null;

        try {
            InputStream is = contexte.getAssets().open(nomFichier);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            // Une erreur s'est produite (on la journalise).
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
