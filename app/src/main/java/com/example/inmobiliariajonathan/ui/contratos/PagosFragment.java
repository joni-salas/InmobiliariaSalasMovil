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

import com.example.inmobiliariajonathan.databinding.PagosFragmentBinding;
import com.example.inmobiliariajonathan.modelo.Pago;

import java.util.List;

public class PagosFragment extends Fragment {

    private PagosViewModel pagosViewModel;
    private PagosAdapter adapter;
    private PagosFragmentBinding binding;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = PagosFragmentBinding.inflate(inflater, container, false);

        inicializar();
        return binding.getRoot();
    }

    private void inicializar() {
        pagosViewModel = new ViewModelProvider(this).get(PagosViewModel.class);

        pagosViewModel.getPagos().observe(getViewLifecycleOwner(), pagos -> {
            if (pagos != null) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 1);
                binding.rvPagosContrato.setLayoutManager(gridLayoutManager);

                adapter = new PagosAdapter(requireContext(), pagos);
                binding.rvPagosContrato.setAdapter(adapter);
            }
        });

        pagosViewModel.cargarPagosDeContratos(getArguments());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
