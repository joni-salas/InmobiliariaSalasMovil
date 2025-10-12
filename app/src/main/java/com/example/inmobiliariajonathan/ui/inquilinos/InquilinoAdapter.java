package com.example.inmobiliariajonathan.ui.inquilinos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.modelo.Inquilino;
import com.example.inmobiliariajonathan.ui.inmuebles.InmuebleAdapter;

import java.util.List;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.ViewHolder>{
    private Context context;
    private List<Inmueble> inmuebles;
    private LayoutInflater inflater;



    public InquilinoAdapter(Context context, List<Inmueble> inmuebles, LayoutInflater inflater) {
        this.context = context;
        this.inmuebles = inmuebles;
        this.inflater = inflater;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_inquilino_fragment, parent, false);
        return new InquilinoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);

        holder.tvDireccion.setText(inmuebles.get(position).getDireccion());
        holder.tvPrecio.setText("$" + inmuebles.get(position).getPrecio());
        Glide.with(context)
                .load("http://192.168.0.112:45455" + inmueble.getImagen())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivImagenInmueble);
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPrecio;
        TextView tvDireccion;
        ImageView ivImagenInmueble;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImagenInmueble = itemView.findViewById(R.id.ivImagenInmueble);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Inmueble inmueble = inmuebles.get(getAdapterPosition());
                    bundle.putSerializable("inmueble", inmueble);            //este "inmueble" es un tag
                    Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main).navigate(R.id.inquilinoFragment, bundle);
                }
            });
        }
    }
}
