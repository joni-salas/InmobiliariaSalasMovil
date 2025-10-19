package com.example.inmobiliariajonathan.ui.contratos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariajonathan.databinding.FragmentContratosBinding;
import com.example.inmobiliariajonathan.modelo.Inmueble;

import java.util.List;

public class ContratosFragment extends Fragment {

    private FragmentContratosBinding binding;
    private ContratosViewModel contratosViewModel;
    private InmueblesAlquiladosAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflar el binding
        binding = FragmentContratosBinding.inflate(inflater, container, false);

        // Inicializar ViewModel
        contratosViewModel = new ViewModelProvider(this).get(ContratosViewModel.class);

        inicializarRecyclerView();

        // Cargar datos
        contratosViewModel.cargarInmueblesAlquilados();

        return binding.getRoot();
    }

    private void inicializarRecyclerView() {
        contratosViewModel.getInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            if (inmuebles != null) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                binding.rvContratos.setLayoutManager(gridLayoutManager);

                adapter = new InmueblesAlquiladosAdapter(getContext(), inmuebles);
                binding.rvContratos.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evitar fugas de memoria
    }
}
