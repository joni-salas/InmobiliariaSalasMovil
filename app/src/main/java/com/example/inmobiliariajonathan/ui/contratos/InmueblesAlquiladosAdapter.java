package com.example.inmobiliariajonathan.ui.contratos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.databinding.ItemInmuebleAlquiladoBinding;
import com.example.inmobiliariajonathan.modelo.Inmueble;

import java.util.List;

public class InmueblesAlquiladosAdapter extends RecyclerView.Adapter<InmueblesAlquiladosAdapter.ViewHolder> {

    private Context context;
    private List<Inmueble> inmuebles;

    public InmueblesAlquiladosAdapter(Context context, List<Inmueble> inmuebles) {
        this.context = context;
        this.inmuebles = inmuebles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInmuebleAlquiladoBinding binding = ItemInmuebleAlquiladoBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);
        holder.binding.tvDireccion.setText(inmueble.getDireccion());

        Glide.with(context)
                .load("http://192.168.0.112:45455" + inmueble.getImagen())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.ivImagenInmueble);

        holder.binding.getRoot().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", inmueble);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.detalleContratoFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemInmuebleAlquiladoBinding binding;

        public ViewHolder(@NonNull ItemInmuebleAlquiladoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
