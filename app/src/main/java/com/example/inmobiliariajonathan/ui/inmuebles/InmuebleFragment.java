package com.example.inmobiliariajonathan.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.inmobiliariajonathan.databinding.FragmentInmueble2Binding;

public class InmuebleFragment extends Fragment {

    private InmuebleViewModel inmuebleViewModel;
    private FragmentInmueble2Binding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInmueble2Binding.inflate(inflater, container, false);

        inmuebleViewModel = new ViewModelProvider(this).get(InmuebleViewModel.class);

        inicializar();

        return binding.getRoot();
    }

    private void inicializar() {

        inmuebleViewModel.getInmueble().observe(getViewLifecycleOwner(), inmueble -> {
            if (inmueble != null) {
                binding.tvId.setText(String.valueOf(inmueble.getIdInmueble()));
                binding.tvDireccion.setText(inmueble.getDireccion());
                binding.tvTipo.setText(inmueble.getTipo());
                binding.tvAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
                binding.tvPrecio.setText("$" + inmueble.getPrecio());

                boolean estado = "1".equals(inmueble.isEstado());
                binding.cbEstado.setChecked(estado);
                binding.btDarBaja.setText(estado ? "Dar de Baja" : "Dar de Alta");

                Glide.with(getContext())
                        .load("http://192.168.0.112:45455" + inmueble.getImagen())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivImagenInmueble);
            }
        });

        inmuebleViewModel.getTextoBoton().observe(getViewLifecycleOwner(), texto -> {
            if (texto != null) {
                binding.btDarBaja.setText(texto);
            }
        });

        inmuebleViewModel.cargarInmueble(getArguments());

        binding.btDarBaja.setOnClickListener(view -> {
            String texto = binding.btDarBaja.getText().toString();
            inmuebleViewModel.accionBoton(texto, getArguments());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
