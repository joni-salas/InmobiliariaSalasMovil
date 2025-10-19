package com.example.inmobiliariajonathan.ui.inmuebles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.databinding.FragmentInmueblesBinding;


public class InmueblesFragment extends Fragment {

    private InmueblesViewModel inmueblesViewModel;
    private InmuebleAdapter adapter;
    private FragmentInmueblesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentInmueblesBinding.inflate(inflater, container, false);

        binding.fab.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.crarInmuebleFragment);
        });

        inicializar();

        return binding.getRoot();
    }

    private void inicializar() {
        inmueblesViewModel = new ViewModelProvider(this).get(InmueblesViewModel.class);

        inmueblesViewModel.getInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            if (inmuebles != null) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                binding.rvInmuebles.setLayoutManager(gridLayoutManager);

                adapter = new InmuebleAdapter(getContext(), inmuebles);
                binding.rvInmuebles.setAdapter(adapter);
            }
        });

        inmueblesViewModel.cargarInmuebles();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
