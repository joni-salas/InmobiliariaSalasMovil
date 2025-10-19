package com.example.inmobiliariajonathan.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.databinding.FragmentInicioBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mapa;
    private LatLng INMOBILIARIA = new LatLng(-33.1850233, -66.3127732);


    private FragmentInicioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InicioViewModel inicioViewModel =
                new ViewModelProvider(this).get(InicioViewModel.class);

        //View root = inflater.inflate(R.layout.fragment_inicio, container, false);
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frMap);

        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;
        CameraPosition camPos = new CameraPosition.Builder()
                .target(INMOBILIARIA)
                .zoom(19)
                .bearing(45)
                .tilt(70).build();
        CameraUpdate camUpdICT = CameraUpdateFactory.newCameraPosition(camPos);
        mapa.animateCamera(camUpdICT);

        mapa.addMarker(new MarkerOptions().position(INMOBILIARIA)).setTitle("San Luis");
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
}