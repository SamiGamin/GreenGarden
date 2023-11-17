package com.greengarden.Noticias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greengarden.R;

import java.util.ArrayList;

public class NtAdapter extends RecyclerView.Adapter<NtAdapter.MiViewholder> {
    Context context;
    ArrayList<Noticiaslist> listanoticias;

    public NtAdapter(Context context, ArrayList<Noticiaslist> listanoticias) {
        this.context = context;
        this.listanoticias = listanoticias;
    }

    @NonNull
    @Override
    public NtAdapter.MiViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listanoticias, parent, false);

        return new MiViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NtAdapter.MiViewholder holder, int position) {

        Noticiaslist noticia = listanoticias.get(position);
        holder.Titulo.setText(noticia.getTitulo());
        holder.Fecha.setText(noticia.getFecha());
        holder.Noticia.setText(noticia.getNoticia());
    }

    @Override
    public int getItemCount() {
        return listanoticias.size();
    }

    public class MiViewholder extends RecyclerView.ViewHolder {
        TextView Titulo, Fecha, Noticia;
        public MiViewholder(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.listtitulo);
            Fecha = itemView.findViewById(R.id.listfecha);
            Noticia = itemView.findViewById(R.id.consejo_siembra);
        }
    }
}
