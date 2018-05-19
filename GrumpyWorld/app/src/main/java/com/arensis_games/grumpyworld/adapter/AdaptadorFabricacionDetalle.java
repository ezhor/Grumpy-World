package com.arensis_games.grumpyworld.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.Equipable;
import com.arensis_games.grumpyworld.model.MaterialNecesario;
import com.arensis_games.grumpyworld.model.Zona;

/**
 * Created by dparrado on 14/11/17.
 */

public class AdaptadorFabricacionDetalle<T> extends ArrayAdapter<T> {

    private Context context;
    private ViewHolder holder;
    private MaterialNecesario materialNecesario;
    private GestoraGUI gesGUI = new GestoraGUI();


    public AdaptadorFabricacionDetalle(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
    }

    public AdaptadorFabricacionDetalle(Context context, int resource, int textViewResourceId, Zona[] zonas) {
        super(context, resource, textViewResourceId, (T[])zonas);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        view = convertView;
        materialNecesario = (MaterialNecesario) getItem(position);

        if(view==null){
            view = inflater.inflate(R.layout.fila_material, null);
            holder = new ViewHolder(view, R.id.tvNombre, R.id.tvCantidad, materialNecesario);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.getTvNombre().setText(gesGUI.getNombreMaterial(context.getResources(), materialNecesario.getNombre()));
        holder.getTvCantidad().setText(String.valueOf(materialNecesario.getCantidad())+" / "+String.valueOf(materialNecesario.getCantidadNecesaria()));
        if(materialNecesario.getCantidad() >= materialNecesario.getCantidadNecesaria()){
            holder.getTvCantidad().setTextColor(context.getResources().getColor(R.color.verde));
        }else{
            holder.getTvCantidad().setTextColor(context.getResources().getColor(R.color.rojo));
        }

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
        private MaterialNecesario materialNecesario;

        public ViewHolder(View view, int idNombre, int idCantidad, MaterialNecesario materialNecesario) {
            this.tvNombre = view.findViewById(idNombre);
            this.tvCantidad = view.findViewById(idCantidad);
            this.materialNecesario = materialNecesario;
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

        public MaterialNecesario getMaterialNecesario() {
            return materialNecesario;
        }

        public void setMaterialNecesario(MaterialNecesario materialNecesario) {
            this.materialNecesario = materialNecesario;
        }
    }

}

