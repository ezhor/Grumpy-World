package com.arensis_games.grumpyworld.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.Amigo;

/**
 * Created by dparrado on 14/11/17.
 */

public class AdaptadorAmigos<T> extends ArrayAdapter<T> {

    private Context context;
    private ViewHolder holder;
    private Amigo amigo;
    private GestoraGUI gesGUI = new GestoraGUI();
    private View.OnClickListener onClickListener;


    public AdaptadorAmigos(Context context, int resource, int textViewResourceId, T[] objects, View.OnClickListener onClickListener) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        view = convertView;
        amigo = (Amigo) getItem(position);

        if(view==null){
            view = inflater.inflate(R.layout.fila_amigo, null);
            holder = new ViewHolder(view, R.id.ivRango, R.id.tvNombre, R.id.tvHonor, R.id.tvNivel, R.id.ibBorrarAmigo, amigo);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.getIvRango().setImageDrawable(gesGUI.getDrawableRango(context.getResources(), amigo.getRango()));
        holder.getTvNombre().setText(amigo.getNombre());
        holder.getTvHonor().setText(String.valueOf(amigo.getHonor()));
        holder.getTvNivel().setText(String.valueOf(context.getString(R.string.nivel, amigo.getNivel())));
        holder.getIbBorrarAmigo().setOnClickListener(this.onClickListener);
        holder.getIbBorrarAmigo().setTag(amigo.getId());

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
        private ImageView ivRango;
        private TextView tvNombre;
        private TextView tvHonor;
        private TextView tvNivel;
        private ImageButton ibBorrarAmigo;
        private Amigo amigo;

        public ViewHolder(View view, int idRango, int idNombre, int idHonor, int idNivel, int idBorrarAmigo, Amigo amigo) {
            this.ivRango = view.findViewById(idRango);
            this.tvNombre = view.findViewById(idNombre);
            this.tvHonor = view.findViewById(idHonor);
            this.tvNivel = view.findViewById(idNivel);
            this.ibBorrarAmigo = view.findViewById(idBorrarAmigo);
            this.amigo = amigo;
        }

        public ImageView getIvRango() {
            return ivRango;
        }

        public void setIvRango(ImageView ivRango) {
            this.ivRango = ivRango;
        }

        public TextView getTvNombre() {
            return tvNombre;
        }

        public void setTvNombre(TextView tvNombre) {
            this.tvNombre = tvNombre;
        }

        public TextView getTvHonor() {
            return tvHonor;
        }

        public void setTvHonor(TextView tvHonor) {
            this.tvHonor = tvHonor;
        }

        public TextView getTvNivel() {
            return tvNivel;
        }

        public void setTvNivel(TextView tvNivel) {
            this.tvNivel = tvNivel;
        }

        public ImageButton getIbBorrarAmigo() {
            return ibBorrarAmigo;
        }

        public void setIbBorrarAmigo(ImageButton ibBorrarAmigo) {
            this.ibBorrarAmigo = ibBorrarAmigo;
        }

        public Amigo getAmigo() {
            return amigo;
        }

        public void setAmigo(Amigo amigo) {
            this.amigo = amigo;
        }
    }

}

