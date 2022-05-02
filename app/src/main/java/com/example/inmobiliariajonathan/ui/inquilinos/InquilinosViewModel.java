package com.example.inmobiliariajonathan.ui.inquilinos;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.request.ApiClient;

import java.util.ArrayList;

public class InquilinosViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Inmueble>> inmuebles;
    private Context context;
    public InquilinosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

    public LiveData<ArrayList<Inmueble>> getInmuebles() {
        if (inmuebles == null) {
            inmuebles = new MutableLiveData<>();
        }
        return inmuebles;
    }

    public void cargarInmuebles() {
        //Acá traemos todos los inmuebles de la base de datos
        ApiClient api= ApiClient.getApi();
        ArrayList<Inmueble> inmuebles=api.obtnerPropiedades();
        this.inmuebles.setValue(inmuebles);

    }
}