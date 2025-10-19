package com.example.inmobiliariajonathan.ui.inmuebles;

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
import com.example.inmobiliariajonathan.databinding.ItemInmuebleFragmentBinding;
import com.example.inmobiliariajonathan.modelo.Inmueble;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolder> {

    private Context context;
    private List<Inmueble> inmuebles;

    public InmuebleAdapter(Context context, List<Inmueble> inmuebles) {
        this.context = context;
        this.inmuebles = inmuebles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInmuebleFragmentBinding binding = ItemInmuebleFragmentBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);

        holder.binding.tvDireccion.setText(inmueble.getDireccion());
        holder.binding.tvPrecio.setText("$" + inmueble.getPrecio());

        Glide.with(context)
                .load("http://192.168.0.112:45455" + inmueble.getImagen())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.ivImagenInmueble);

        // Click listener para navegar
        holder.binding.getRoot().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", inmueble);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.inmuebleFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemInmuebleFragmentBinding binding;

        public ViewHolder(@NonNull ItemInmuebleFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
