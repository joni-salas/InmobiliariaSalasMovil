package com.example.inmobiliariajonathan.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.modelo.Inmueble;

public class InmuebleFragment extends Fragment {

    private InmuebleViewModel inmuebleViewModel;
    private TextView tvId;
    private TextView tvDireccion;
    private TextView tvTipo;
    private TextView tvUso;
    private TextView tvAmbientes;
    private TextView tvPrecio;
    private CheckBox cbEstado;
    private ImageView ivImagenInmueble;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inmueble2, container, false);
        inicializar(root);
        return root;
    }

    private void inicializar(View view) {
        tvId = view.findViewById(R.id.tvId);
        tvDireccion = view.findViewById(R.id.tvDireccion);
        tvTipo = view.findViewById(R.id.tvTipo);
        tvUso = view.findViewById(R.id.tvUso);
        tvAmbientes = view.findViewById(R.id.tvAmbientes);
        tvPrecio = view.findViewById(R.id.tvPrecio);
        cbEstado = view.findViewById(R.id.cbEstado);
        ivImagenInmueble = view.findViewById(R.id.ivImagenInmueble);
        inmuebleViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InmuebleViewModel.class);
        inmuebleViewModel.getInmueble().observe(getActivity(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                tvId.setText(inmueble.getIdInmueble() + "");
                tvDireccion.setText(inmueble.getDireccion());
                tvTipo.setText(inmueble.getTipo());
                tvUso.setText(inmueble.getUso());
                tvAmbientes.setText(inmueble.getAmbientes() + "");
                tvPrecio.setText("$" + inmueble.getPrecio());
                cbEstado.setChecked(inmueble.isEstado());

                Glide.with(getContext())
                        .load(inmueble.getImagen())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivImagenInmueble);


            }
        });
        inmuebleViewModel.cargarInmueble(getArguments()); // getArguments es para obtener el objeto bundle
    }

}