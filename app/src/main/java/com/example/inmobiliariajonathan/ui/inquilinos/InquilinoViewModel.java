package com.example.inmobiliariajonathan.ui.inquilinos;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.modelo.Inquilino;
import com.example.inmobiliariajonathan.request.ApiClient;

import java.util.ArrayList;

public class InquilinoViewModel extends ViewModel {
    private MutableLiveData<Inquilino> inquilino;
    public InquilinoViewModel(){super ();}

    public LiveData<Inquilino> getInquilino(){
        if(inquilino == null){
            inquilino = new MutableLiveData<>();
        }
        return inquilino;

    }
    public void cargarInquilino(Bundle bundle){
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble") ;  //este tag es el que viene de inquilino adapter
        ApiClient api= ApiClient.getApi();
        Inquilino inqui = api.obtenerInquilino(inmueble);
        this.inquilino.setValue(inqui);
    }
}