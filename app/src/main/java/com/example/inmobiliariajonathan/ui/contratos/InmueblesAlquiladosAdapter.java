package com.example.inmobiliariajonathan.ui.contratos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.modelo.Inmueble;

import java.util.List;

public class InmueblesAlquiladosAdapter extends RecyclerView.Adapter<InmueblesAlquiladosAdapter.ViewHolder>{

    private Context context;
    private List<Inmueble> inmuebles;
    private LayoutInflater inflater;



    public InmueblesAlquiladosAdapter(Context context, List<Inmueble> inmuebles, LayoutInflater inflater) {
        this.context = context;
        this.inmuebles = inmuebles;
        this.inflater = inflater;
    }
    @NonNull
    @Override
    public InmueblesAlquiladosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_inmueble_alquilado, parent, false);
        return new InmueblesAlquiladosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InmueblesAlquiladosAdapter.ViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);
        holder.tvDireccion.setText(inmueble.getDireccion());

        Glide.with(context)
                .load("http://192.168.0.172:45455" + inmueble.getImagen())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivImagenInmueble);
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDireccion;
        ImageView ivImagenInmueble;
        Button btnVer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImagenInmueble    = itemView.findViewById(R.id.ivImagenInmueble);
            //btnVer              = itemView.findViewById(R.id.btnVer);
            tvDireccion         = itemView.findViewById(R.id.tvDireccion);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Inmueble inmueble = inmuebles.get(getAdapterPosition());
                    bundle.putSerializable("inmueble", inmueble);
                    Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main).navigate(R.id.detalleContratoFragment, bundle);
                }
            });
        }
    }
}
