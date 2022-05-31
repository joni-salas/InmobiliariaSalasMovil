package com.example.inmobiliariajonathan.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.modelo.Pago;

import java.util.ArrayList;
import java.util.List;

public class PagosFragment extends Fragment {

    private RecyclerView rvPagosContrato;
    private PagosAdapter adapter;
    private PagosViewModel pagosViewModel;
    private Context context;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.pagos_fragment, container, false);
        context = root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        rvPagosContrato = root.findViewById(R.id.rvPagosContrato);
        pagosViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PagosViewModel.class);
        pagosViewModel.getPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false);
                rvPagosContrato.setLayoutManager(gridLayoutManager);
                adapter = new PagosAdapter(context,pagos,getLayoutInflater());
                rvPagosContrato.setAdapter(adapter);
            }
        });
        pagosViewModel.cargarPagosDeContratos(getArguments());
    }

}