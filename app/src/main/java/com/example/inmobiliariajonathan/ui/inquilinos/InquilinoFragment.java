package com.example.inmobiliariajonathan.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariajonathan.databinding.Inquilino2FragmentBinding;
import com.example.inmobiliariajonathan.modelo.Inquilino;

public class InquilinoFragment extends Fragment {

    private InquilinoViewModel inquilinoViewModel;
    private Inquilino2FragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = Inquilino2FragmentBinding.inflate(inflater, container, false);

        inicializar();

        return binding.getRoot();
    }

    private void inicializar() {
        inquilinoViewModel = new ViewModelProvider(this).get(InquilinoViewModel.class);

        inquilinoViewModel.getInquilino().observe(getViewLifecycleOwner(), inquilino -> {
            if (inquilino != null) {
                binding.TVInqCodigo.setText(String.valueOf(inquilino.getIdInquilino()));
                binding.TVInqNombre.setText(inquilino.getNombre());
                binding.TVInqApellido.setText(inquilino.getApellido());
                binding.TVInqDni.setText(String.valueOf(inquilino.getDNI()));
                binding.TVInqEmail.setText(inquilino.getEmail());
                binding.TVInqTelefono.setText(inquilino.getTelefono());
            }
        });

        inquilinoViewModel.getMensajeMutable().observe(getViewLifecycleOwner(), mensaje -> {
            if(mensaje != null){
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
            }
        });

        inquilinoViewModel.cargarInquilino(getArguments());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
