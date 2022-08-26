package ca.collegelacite.es03_p22;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {


    Context context;
    ArrayList<Pays> listePays;
    LayoutInflater inflater;

    public Adapter(Context context, ArrayList<Pays> listePays) {
        this.context = context;
        this.listePays = listePays;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listePays.size();
    }

    @Override
    public Pays getItem(int position) {
        return listePays.get(position);

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_layout, null);

        Pays pays = getItem(position);

        ImageView drapeau = view.findViewById(R.id.drapeau);
        pays.drapeauDansImageView(drapeau);
        TextView nom_pays = view.findViewById(R.id.nom_pays);
        nom_pays.setText(pays.getNom());

        return view;
    }
}
