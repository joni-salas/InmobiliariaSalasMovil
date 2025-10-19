package com.example.inmobiliariajonathan.ui.inquilinos;

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
import com.example.inmobiliariajonathan.databinding.ItemInquilinoFragmentBinding;
import com.example.inmobiliariajonathan.modelo.Inmueble;

import java.util.List;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.ViewHolder> {

    private final Context context;
    private final List<Inmueble> inmuebles;
    private final LayoutInflater inflater;

    public InquilinoAdapter(Context context, List<Inmueble> inmuebles, LayoutInflater inflater) {
        this.context = context;
        this.inmuebles = inmuebles;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInquilinoFragmentBinding binding = ItemInquilinoFragmentBinding.inflate(inflater, parent, false);
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

        holder.binding.getRoot().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", inmueble);

            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.inquilinoFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemInquilinoFragmentBinding binding;

        public ViewHolder(@NonNull ItemInquilinoFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
