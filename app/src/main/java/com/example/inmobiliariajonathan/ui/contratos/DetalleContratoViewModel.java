package com.example.inmobiliariajonathan.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariajonathan.modelo.Contrato;
import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {

    private final MutableLiveData<Contrato> contrato = new MutableLiveData<>();
    private final Context context;
    private final ApiClient api;
    private final ApiClient.RetrofitService rfs;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        api = new ApiClient();
        rfs = ApiClient.getMyApiClient();
    }

    public LiveData<Contrato> getContrato() {
        return contrato;
    }

    public void cargarInmuebleAlquilados(Bundle bundle) {
        if (bundle == null) return;

        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        if (inmueble == null) return;

        Call<Contrato> contratoXID = rfs.cargarContrato(inmueble.getIdInmueble(), api.getToken(context));
        contratoXID.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()) {
                    contrato.postValue(response.body());
                } else {
                    Log.d("DetalleContratoVM", "Error onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.d("DetalleContratoVM", "Error onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
