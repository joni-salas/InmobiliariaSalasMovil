package com.example.inmobiliariajonathan.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariajonathan.modelo.Contrato;
import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.request.ApiClient;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> contrato;
    private Context context;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getBaseContext();
    }

    public LiveData<Contrato> getContrato() {
        if (contrato == null) {
            contrato = new MutableLiveData<>();
        }
        return contrato;
    }

    public void cargarInmuebleAlquilados(Bundle bundle) {
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        ApiClient api = ApiClient.getApi();
        this.contrato.setValue(api.obtenerContratoVigente(inmueble));
    }
}