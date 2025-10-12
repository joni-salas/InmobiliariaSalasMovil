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

import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CrearInmuebleFragment extends Fragment {

    private CrearInmuebleViewModel mViewModel;
    private Context context;
    private EditText etDireccion ,etSuperficie,etAmbientes,etPrecio,etTipo;
    private Button btCrear;
    private Button btCargarImagen;
    private ImageView imagenView;

    public static CrearInmuebleFragment newInstance() {
        return new CrearInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View root =  inflater.inflate(R.layout.fragment_crear_inmueble, container, false);

        context= root.getContext();
        inicializar(root);

        return root;
    }

    private void inicializar(View view) {
        etDireccion = view.findViewById(R.id.etDireccion);
        etSuperficie = view.findViewById(R.id.etSuperficie);
        etAmbientes = view.findViewById(R.id.etAmbientes);
        etPrecio = view.findViewById(R.id.etPrecio);
        etTipo = view.findViewById(R.id.etPrecio);
        btCrear = view.findViewById(R.id.btCrear);
        imagenView = view.findViewById(R.id.imagenView);
        btCargarImagen = view.findViewById(R.id.btCargarImagen);

        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(CrearInmuebleViewModel.class);

        btCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inmueble inmueble= new Inmueble();

                inmueble.setDireccion(etDireccion.getText().toString());
                inmueble.setSuperficie(etSuperficie.getText().toString());
                inmueble.setAmbientes(etAmbientes.getText().toString());
                inmueble.setPrecio(etPrecio.getText().toString());
                inmueble.setTipo(etTipo.getText().toString());
                inmueble.setEstado("1");
                inmueble.setImagen("");
                inmueble.setImgGuardar(mViewModel.convertirImagen(imagenView));

                if(mViewModel.crearInmueble(inmueble)){
                    Navigation.findNavController((getActivity()), R.id.inmuebleFragment).navigate(R.id.nav_inmueble); // no funciona
                }


            }
        });

        btCargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }
        });
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
        imagenView.setImageURI(path);  //seteo en el imagenView la imagen seleccionada
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }


}