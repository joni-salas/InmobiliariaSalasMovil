package com.example.inmobiliariajonathan.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariajonathan.databinding.FragmentPerfilBinding;
import com.example.inmobiliariajonathan.modelo.Propietario;
import com.example.inmobiliariajonathan.ui.CambiarContraseña.CambiarContrasenaActivity;

public class PerfilFragment extends Fragment {

    PerfilViewModel perfilViewModel;
    private Propietario propietarioCompleto;

    private FragmentPerfilBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inicializarVista(root);

        perfilViewModel.getUsuarioMutable().observe(getViewLifecycleOwner(), propietario -> {
            propietarioCompleto = propietario;
            binding.etDniPerfil.setText(propietario.getDni());
            binding.etApellidoPerfil.setText(propietario.getApellido());
            binding.etNombrePerfil.setText(propietario.getNombre());
            binding.etTelefonoPerfil.setText(propietario.getTelefono());
            binding.etEmailPerfil.setText(propietario.getEmail());
        });

        perfilViewModel.getEstadoMutable().observe(getViewLifecycleOwner(), aBoolean -> {
            binding.etDniPerfil.setEnabled(aBoolean);
            binding.etApellidoPerfil.setEnabled(aBoolean);
            binding.etNombrePerfil.setEnabled(aBoolean);
            binding.etTelefonoPerfil.setEnabled(aBoolean);
            binding.etEmailPerfil.setEnabled(aBoolean);
        });

        perfilViewModel.getTextoBoton().observe(getViewLifecycleOwner(), s -> {
            binding.btEditarPerfil.setText(s);
        });

        perfilViewModel.traerDatos();

        return root;
    }

    private void inicializarVista(View root) {

        binding.btEditarPerfil.setOnClickListener(view -> {
            Propietario propietario = propietarioCompleto;
            propietario.setNombre(binding.etNombrePerfil.getText().toString());
            propietario.setApellido(binding.etApellidoPerfil.getText().toString());
            propietario.setDni(binding.etDniPerfil.getText().toString());
            propietario.setTelefono(binding.etTelefonoPerfil.getText().toString());
            propietario.setEmail(binding.etEmailPerfil.getText().toString());

            String texto = binding.btEditarPerfil.getText().toString();
            perfilViewModel.accionBoton(texto, propietario);
        });

        binding.btOlvidecontrasena.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CambiarContrasenaActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}