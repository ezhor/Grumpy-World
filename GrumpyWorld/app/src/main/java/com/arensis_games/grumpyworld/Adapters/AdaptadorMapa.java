package com.arensis_games.grumpyworld.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arensis_games.grumpyworld.Gestoras.GestoraGUI;
import com.arensis_games.grumpyworld.Models.Zona;
import com.arensis_games.grumpyworld.R;

/**
 * Created by dparrado on 14/11/17.
 */

public class AdaptadorMapa<T> extends ArrayAdapter<T> {

    private Context context;
    private ViewHolder holder;
    private Zona zona;
    private GestoraGUI gesGUI = new GestoraGUI();


    public AdaptadorMapa(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
    }

    public AdaptadorMapa(Context context, int resource, int textViewResourceId, Zona[] zonas) {
        super(context, resource, textViewResourceId, (T[])zonas);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        view = convertView;
        zona = (Zona) getItem(position);

        if(view==null){
            view = inflater.inflate(R.layout.fila_mapa, null);
            holder = new ViewHolder(view, R.id.tvNombre, R.id.tvNivel, zona);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.getTvNombre().setText(gesGUI.getNombreZona(context.getResources(), zona.getNombre()));
        holder.getTvNivel().setText(context.getString(R.string.nivel, zona.getNivel()));

        return view;
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }

    @Override
    public int getViewTypeCount(){
        return 1;
    }

    public class ViewHolder{
        private TextView tvNombre;
        private TextView tvNivel;
        private Zona zona;

        public ViewHolder(View view, int idNombre, int idNivel, Zona zona) {
            this.tvNombre = view.findViewById(idNombre);
            this.tvNivel = view.findViewById(idNivel);
            this.zona = zona;
        }

        public TextView getTvNombre() {
            return tvNombre;
        }

        public void setTvNombre(TextView tvNombre) {
            this.tvNombre = tvNombre;
        }

        public TextView getTvNivel() {
            return tvNivel;
        }

        public void setTvNivel(TextView tvNivel) {
            this.tvNivel = tvNivel;
        }

        public Zona getZona() {
            return zona;
        }

        public void setZona(Zona zona) {
            this.zona = zona;
        }
    }

}

