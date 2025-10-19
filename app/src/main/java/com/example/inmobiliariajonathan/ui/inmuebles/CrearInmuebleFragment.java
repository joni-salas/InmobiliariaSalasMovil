package com.example.inmobiliariajonathan.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.databinding.FragmentCrearInmuebleBinding;
import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CrearInmuebleFragment extends Fragment {

    private CrearInmuebleViewModel mViewModel;
    private FragmentCrearInmuebleBinding binding;

    public static CrearInmuebleFragment newInstance() {
        return new CrearInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = FragmentCrearInmuebleBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(CrearInmuebleViewModel.class);

        inicializar();

        return binding.getRoot();

    }

    private void inicializar() {


        mViewModel.getMensajeMutable().observe(getViewLifecycleOwner(), mensaje -> {
            if (mensaje != null) {
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getInmuebleCreadoMutable().observe(getViewLifecycleOwner(), creado -> {
            if (Boolean.TRUE.equals(creado)) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_inmueble);
            }
        });

        binding.btCrear.setOnClickListener(v -> {
            Inmueble inmueble = new Inmueble();
            inmueble.setDireccion(binding.etDireccion.getText().toString());
            inmueble.setSuperficie(binding.etSuperficie.getText().toString());
            inmueble.setAmbientes(binding.etAmbientes.getText().toString());
            inmueble.setPrecio(binding.etPrecio.getText().toString());
            inmueble.setTipo(binding.etTipo.getText().toString());
            inmueble.setEstado("0");
            inmueble.setImagen("");
            inmueble.setImgGuardar(mViewModel.convertirImagen(binding.imagenView));

            mViewModel.crearInmueble(inmueble);
        });

        binding.btCargarImagen.setOnClickListener(v -> cargarImagen());
    }

    public void cargarImagen(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);  //intent para abrir la galeria
        intent.setType("image/");
        Bundle numero = new Bundle();
        numero.putInt("id",10);
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion..."),10);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("registro", "onActivityResult");
        Uri path=data.getData();  //seteo al Objeto Uri la imagen seleccionada de la galeria
        binding.imagenView.setImageURI(path);  //seteo en el imagenView la imagen seleccionada
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}