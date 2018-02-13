package com.arensis_games.grumpyworld.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arensis_games.grumpyworld.R;

/**
 * Created by dparrado on 14/11/17.
 */

public class AdaptadorDrawer<T> extends ArrayAdapter<T> {

    private String[] items;
    private Context context;
    private ViewHolder holder;
    private String elemento;


    public AdaptadorDrawer(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.items = (String[]) objects;
        this.context = context;
    }

    public AdaptadorDrawer(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, (T[])objects);
        this.items = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        view = convertView;

        if(view==null){
            view = inflater.inflate(R.layout.fila_drawer, null);
            holder = new ViewHolder(view, R.id.icono, R.id.texto);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }


        elemento = (String) getItem(position);
        holder.getTextView().setText(elemento);


        return view;
    }

    @Override
    public int getItemViewType(int position){
        return 0;
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

