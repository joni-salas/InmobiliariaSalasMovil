package com.example.inmobiliariajonathan.ui.inquilinos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmobiliariajonathan.databinding.InquilinosFragmentBinding;

public class InquilinosFragment extends Fragment {

    private InquilinosViewModel inquilinosViewModel;
    private InquilinoAdapter adapter;
    private Context context;
    private InquilinosFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = InquilinosFragmentBinding.inflate(inflater, container, false);
        context = binding.getRoot().getContext();

        inicializar();

        return binding.getRoot();
    }

    private void inicializar() {
        inquilinosViewModel = new ViewModelProvider(this).get(InquilinosViewModel.class);

        inquilinosViewModel.getMensajeMutable().observe(getViewLifecycleOwner(), mensaje -> {
            if (mensaje != null) {
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
            }
        });


        inquilinosViewModel.getInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            if (inmuebles != null) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                binding.rvInmuebles.setLayoutManager(gridLayoutManager);

                adapter = new InquilinoAdapter(context, inmuebles, getLayoutInflater());
                binding.rvInmuebles.setAdapter(adapter);
            }
        });

        inquilinosViewModel.cargarInmuebles();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
