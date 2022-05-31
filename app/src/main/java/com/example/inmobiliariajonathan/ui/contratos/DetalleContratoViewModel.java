package com.example.inmobiliariajonathan.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariajonathan.modelo.Contrato;
import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> contrato;
    private Context context;
    private ApiClient api;
    private ApiClient.RetrofitService rfs;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getBaseContext();
        api = new ApiClient();
        rfs=ApiClient.getMyApiClient();
    }

    public LiveData<Contrato> getContrato() {
        if (contrato == null) {
            contrato = new MutableLiveData<>();
        }
        return contrato;
    }

    public void cargarInmuebleAlquilados(Bundle bundle) {
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");

        Call<Contrato> contratoXID = rfs.cargarContrato(inmueble.getIdInmueble(),api.getToken(context));

        contratoXID.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful()){
                    contrato.setValue(response.body());
                }else{
                    Log.d("Error onresponse: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.d("Error onFailure: ", t.getLocalizedMessage());
            }
        });


    }
}