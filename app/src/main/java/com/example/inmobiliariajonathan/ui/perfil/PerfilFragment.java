package com.example.inmobiliariajonathan.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.databinding.FragmentPerfilBinding;
import com.example.inmobiliariajonathan.modelo.Propietario;

public class PerfilFragment extends Fragment {

    private EditText etDni, etNombre, etApellido, etTelefono, etContraseña, etMail;
    private Button btEditar;
    PerfilViewModel perfilViewModel;
    private Propietario propietarioCompleto;

    //private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        //binding = FragmentPerfilBinding.inflate(inflater, container, false);

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        inicializarVista(root);

        perfilViewModel.getUsuarioMutable().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                propietarioCompleto = propietario;
                etDni.setText(propietario.getDni());
                etApellido.setText(propietario.getApellido());
                etNombre.setText(propietario.getNombre());
                etTelefono.setText(propietario.getTelefono());
                etMail.setText(propietario.getEmail());
                //etContraseña.setText(propietario.getContraseña());
            }
        });
        perfilViewModel.getEstadoMutable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                etDni.setEnabled(aBoolean);
                etApellido.setEnabled(aBoolean);
                etNombre.setEnabled(aBoolean);
                etTelefono.setEnabled(aBoolean);
                etMail.setEnabled(aBoolean);
                etContraseña.setEnabled(aBoolean);
            }
        });

        perfilViewModel.getTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                btEditar.setText(s);
            }
        });

        //final TextView textView = binding.textPerfil;
        //perfilViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        perfilViewModel.traerDatos();

        return root;
    }

    private void inicializarVista(View root) {
        etDni = root.findViewById(R.id.etDniPerfil);
        etNombre =root.findViewById(R.id.etNombrePerfil);
        etApellido =root.findViewById(R.id.etApellidoPerfil);
        etTelefono =root.findViewById(R.id.etTelefonoPerfil);
        etMail =root.findViewById(R.id.etEmailPerfil);
        btEditar =root.findViewById(R.id.btEditarPerfil);

        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Propietario propietario = propietarioCompleto;
                propietario.setNombre(etNombre.getText().toString());
                propietario.setApellido(etApellido.getText().toString());
                propietario.setDni((etDni.getText().toString()));
                propietario.setTelefono(etTelefono.getText().toString());
                propietario.setEmail(etMail.getText().toString());
                //propietario.setContraseña(etContraseña.getText().toString());

                String texto=btEditar.getText().toString();
                perfilViewModel.accionBoton(texto,propietario);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}