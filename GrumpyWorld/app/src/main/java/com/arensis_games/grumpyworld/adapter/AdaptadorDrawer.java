package com.arensis_games.grumpyworld.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.R;

/**
 * Created by dparrado on 14/11/17.
 */

public class AdaptadorDrawer<T> extends ArrayAdapter<T> {

    private Context context;
    private ViewHolder holder;
    private String elemento;
    private GestoraGUI gesGUI = new GestoraGUI();


    public AdaptadorDrawer(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
    }

    public AdaptadorDrawer(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, (T[])objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        view = convertView;

        if(view==null){
            if(getItemViewType(position)==1){
                view = inflater.inflate(R.layout.seccion_drawer, null);
                view.setEnabled(false);
                view.setOnClickListener(null);
            }else{
                view = inflater.inflate(R.layout.fila_drawer, null);
            }
            holder = new ViewHolder(view, R.id.icono, R.id.texto);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }


        elemento = (String) getItem(position);
        holder.getTextView().setText(elemento);
        if(getItemViewType(position)==0){
            holder.getImageView().setImageDrawable(gesGUI.getDrawableIconoMenu(context.getResources(), elemento));
        }

        return view;
    }

    @Override
    public int getItemViewType(int position){
        int tipo = 0;
        if(
            getItem(position).equals(context.getString(R.string.menu_campana)) ||
            getItem(position).equals(context.getString(R.string.menu_multijugador)) ||
            getItem(position).equals(context.getString(R.string.menu_inventario)) ||
            getItem(position).equals(context.getString(R.string.menu_extras))) {
            tipo = 1;
        }
        return tipo;
    }

    @Override
    public int getViewTypeCount(){
        return 2;
    }

    public class ViewHolder{
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View fila, int idImageView, int idTextView){
            this.imageView= fila.findViewById(idImageView);
            this.textView= fila.findViewById(idTextView);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

}

