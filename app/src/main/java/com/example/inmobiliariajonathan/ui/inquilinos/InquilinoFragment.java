package com.example.inmobiliariajonathan.ui.inquilinos;

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
import com.example.inmobiliariajonathan.modelo.Inquilino;
import com.example.inmobiliariajonathan.ui.inmuebles.InmuebleViewModel;

public class InquilinoFragment extends Fragment {

    private InquilinoViewModel inquilinoViewModel;
    private TextView TVInqCodigo;
    private TextView TVInqNombre;
    private TextView TVInqApellido;
    private TextView TVLugarTrabajo;
    private TextView TVInqDni;
    private TextView TVInqEmail;
    private TextView TVInqTelefono;
    private TextView TvInqGaraTelefono;
    private TextView TVInqGarante;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.inquilino2_fragment, container, false);
        inicializar(root);
        return root;
    }

    private void inicializar(View view) {
        TVInqCodigo = view.findViewById(R.id.TVInqCodigo);
        TVInqNombre = view.findViewById(R.id.TVInqNombre);
        TVInqApellido = view.findViewById(R.id.TVInqApellido);
        TVLugarTrabajo = view.findViewById(R.id.TVLugarTrabajo);
        TVInqDni = view.findViewById(R.id.TVInqDni);
        TVInqEmail = view.findViewById(R.id.TVInqEmail);
        TVInqTelefono = view.findViewById(R.id.TVInqTelefono);
        TvInqGaraTelefono = view.findViewById(R.id.TvInqGaraTelefono);
        TVInqGarante = view.findViewById(R.id.TVInqGarante);

        inquilinoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InquilinoViewModel.class);
        inquilinoViewModel.getInquilino().observe(getActivity(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {

                if(inquilino !=null){
                    TVInqCodigo.setText(inquilino.getIdInquilino() + "");
                    TVInqNombre.setText(inquilino.getNombre());
                    TVInqApellido.setText(inquilino.getApellido());
                    TVLugarTrabajo.setText(inquilino.getLugarDeTrabajo());
                    TVInqDni.setText(inquilino.getDNI()+"");
                    TVInqEmail.setText(inquilino.getEmail());
                    TVInqTelefono.setText(inquilino.getTelefono());
                    TvInqGaraTelefono.setText(inquilino.getTelefonoGarante());
                    TVInqGarante.setText(inquilino.getNombreGarante());
                }


            }
        });
        inquilinoViewModel.cargarInquilino(getArguments()); // getArguments es para obtener el objeto bundle
    }

}