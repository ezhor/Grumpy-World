package com.arensis_games.grumpyworld.adapter;

import android.content.Context;
import android.graphics.Typeface;
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
import com.arensis_games.grumpyworld.model.EquipablePoseido;
import com.arensis_games.grumpyworld.model.Zona;

/**
 * Created by dparrado on 14/11/17.
 */

public class AdaptadorEquipamiento<T> extends ArrayAdapter<T> {

    private Context context;
    private ViewHolder holder;
    private EquipablePoseido equipable;
    private GestoraGUI gesGUI = new GestoraGUI();


    public AdaptadorEquipamiento(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        view = convertView;
        equipable = (EquipablePoseido) getItem(position);

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
        if(equipable.isEquipado()){
            holder.getTvNombre().setTypeface(null, Typeface.BOLD_ITALIC);
            holder.getTvBonus().setTypeface(null, Typeface.BOLD_ITALIC);

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
        private ImageView ivIcono;
        private TextView tvNombre;
        private TextView tvBonus;
        private EquipablePoseido equipable;

        public ViewHolder(View view, int idIcono, int idNombre, int idBonus, EquipablePoseido equipable) {
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

        public EquipablePoseido getEquipable() {
            return equipable;
        }

        public void setEquipable(EquipablePoseido equipable) {
            this.equipable = equipable;
        }
    }

}

