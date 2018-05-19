package com.arensis_games.grumpyworld.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.Equipable;
import com.arensis_games.grumpyworld.model.Zona;
import com.arensis_games.grumpyworld.R;

/**
 * Created by dparrado on 14/11/17.
 */

public class AdaptadorFabricacion<T> extends ArrayAdapter<T> {

    private Context context;
    private ViewHolder holder;
    private Equipable equipable;
    private GestoraGUI gesGUI = new GestoraGUI();


    public AdaptadorFabricacion(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
    }

    public AdaptadorFabricacion(Context context, int resource, int textViewResourceId, Zona[] zonas) {
        super(context, resource, textViewResourceId, (T[])zonas);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        view = convertView;
        equipable = (Equipable) getItem(position);

        if(view==null){
            view = inflater.inflate(R.layout.fila_fabricacion, null);
            holder = new ViewHolder(view, R.id.ivIcono, R.id.tvNombre, R.id.tvBonus, equipable);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.getIvIcono().setImageDrawable(gesGUI.getDrawableIconoEquipable(context.getResources(), equipable.getTipo()));
        holder.getTvNombre().setText(gesGUI.getNombreEquipable(context.getResources(), equipable.getNombre()));
        holder.getTvBonus().setText("+"+String.valueOf(equipable.getBonus()));

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
        private ImageView ivIcono;
        private TextView tvNombre;
        private TextView tvBonus;
        private Equipable equipable;

        public ViewHolder(View view, int idIcono, int idNombre, int idBonus, Equipable equipable) {
            this.ivIcono = view.findViewById(idIcono);
            this.tvNombre = view.findViewById(idNombre);
            this.tvBonus = view.findViewById(idBonus);
            this.equipable = equipable;
        }

        public ImageView getIvIcono() {
            return ivIcono;
        }

        public void setIvIcono(ImageView ivIcono) {
            this.ivIcono = ivIcono;
        }

        public TextView getTvNombre() {
            return tvNombre;
        }

        public void setTvNombre(TextView tvNombre) {
            this.tvNombre = tvNombre;
        }

        public TextView getTvBonus() {
            return tvBonus;
        }

        public void setTvBonus(TextView tvBonus) {
            this.tvBonus = tvBonus;
        }

        public Equipable getEquipable() {
            return equipable;
        }

        public void setEquipable(Equipable equipable) {
            this.equipable = equipable;
        }
    }

}

