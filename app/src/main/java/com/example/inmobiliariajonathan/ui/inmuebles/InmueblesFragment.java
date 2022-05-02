package com.example.inmobiliariajonathan.ui.inmuebles;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.databinding.FragmentInmueblesBinding;
import com.example.inmobiliariajonathan.modelo.Inmueble;

import java.util.ArrayList;


public class InmueblesFragment extends Fragment {
    private RecyclerView rvInmuebles;
    private InmueblesViewModel inmueblesViewModel;
    private InmuebleAdapter adapter;
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

        inmueblesViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InmueblesViewModel.class);
        inmueblesViewModel.getInmuebles().observe(getViewLifecycleOwner(), new Observer<ArrayList<Inmueble>>() {
            @Override
            public void onChanged(ArrayList<Inmueble> inmuebles) {
                GridLayoutManager gridLayoutManager= new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
                rvInmuebles.setLayoutManager(gridLayoutManager);
                adapter = new InmuebleAdapter(context, inmuebles, getLayoutInflater());
                rvInmuebles.setAdapter(adapter);
            }
        });
        inmueblesViewModel.cargarInmuebles();
    }
}