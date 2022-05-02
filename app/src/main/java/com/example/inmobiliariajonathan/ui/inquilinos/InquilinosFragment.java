package com.example.inmobiliariajonathan.ui.inquilinos;

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
import android.widget.TextView;

import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.ui.inmuebles.InmuebleAdapter;
import com.example.inmobiliariajonathan.ui.inmuebles.InmueblesViewModel;

import java.util.ArrayList;

public class InquilinosFragment extends Fragment {

    private RecyclerView rvInmuebles;
    private InquilinosViewModel inquilinosViewModel;
    private InquilinoAdapter adapter;
    private Context context;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_inmuebles, container, false);
        context = root.getContext();
        inicializar(root);
        return root;
    }
    private void inicializar(View view) {
        rvInmuebles = view.findViewById(R.id.rvInmuebles);

        inquilinosViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InquilinosViewModel.class);
        inquilinosViewModel.getInmuebles().observe(getViewLifecycleOwner(), new Observer<ArrayList<Inmueble>>() {
            @Override
            public void onChanged(ArrayList<Inmueble> inmuebles) {
                GridLayoutManager gridLayoutManager= new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                rvInmuebles.setLayoutManager(gridLayoutManager);
                adapter = new InquilinoAdapter(context, inmuebles, getLayoutInflater());
                rvInmuebles.setAdapter(adapter);
            }
        });
        inquilinosViewModel.cargarInmuebles();
    }

}