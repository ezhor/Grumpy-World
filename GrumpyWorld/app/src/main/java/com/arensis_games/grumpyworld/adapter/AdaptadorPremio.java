package com.arensis_games.grumpyworld.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.Material;
import com.arensis_games.grumpyworld.R;

/**
 * Created by dparrado on 14/11/17.
 */

public class AdaptadorPremio<T> extends ArrayAdapter<T> {

    private Context context;
    private ViewHolder holder;
    private Material material;
    private GestoraGUI gesGUI = new GestoraGUI();
    private boolean honor;


    public AdaptadorPremio(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.honor = false;
    }
    public AdaptadorPremio(Context context, int resource, int textViewResourceId, T[] objects, boolean honor) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.honor = honor;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        view = convertView;
        material = (Material) getItem(position);

        if(view==null){
            if(honor){
                view = inflater.inflate(R.layout.fila_honor, null);
            }else{
                view = inflater.inflate(R.layout.fila_material, null);
            }
            holder = new ViewHolder(view, R.id.tvNombre, R.id.tvCantidad, material);
            view.setTag(holder);
            view.setEnabled(false);
            view.setOnClickListener(null);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.getTvNombre().setText(gesGUI.getNombreMaterial(context.getResources(), material.getNombre()));
        holder.getTvCantidad().setText("x"+String.valueOf(material.getCantidad()));

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
        private TextView tvCantidad;
        private Material material;

        public ViewHolder(View view, int idNombre, int idCantidad, Material material) {
            this.tvNombre = view.findViewById(idNombre);
            this.tvCantidad = view.findViewById(idCantidad);
            this.material = material;
        }

        public TextView getTvNombre() {
            return tvNombre;
        }

        public void setTvNombre(TextView tvNombre) {
            this.tvNombre = tvNombre;
        }

        public TextView getTvCantidad() {
            return tvCantidad;
        }

        public void setTvCantidad(TextView tvCantidad) {
            this.tvCantidad = tvCantidad;
        }

        public Material getMaterial() {
            return material;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }
    }

}

