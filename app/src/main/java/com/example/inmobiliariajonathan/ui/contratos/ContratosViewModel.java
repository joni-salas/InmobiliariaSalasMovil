package com.example.inmobiliariajonathan.ui.contratos;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Inmueble>> inmuebles = new MutableLiveData<>();
    private final MutableLiveData<String> mensajeError = new MutableLiveData<>();
    private final Context context;
    private final ApiClient.RetrofitService rfs;
    private final ApiClient api;

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        api = new ApiClient();
        rfs = ApiClient.getMyApiClient();
    }

    public LiveData<List<Inmueble>> getInmuebles() {
        return inmuebles;
    }

    public LiveData<String> getMensajeError() {
        return mensajeError;
    }

    public void cargarInmueblesAlquilados() {
        Call<List<Inmueble>> listaInmuebles = rfs.inmueblesPorUsuario(api.getToken(context));

        listaInmuebles.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    inmuebles.postValue(response.body());
                } else {
                    mensajeError.postValue("Error al listar inmuebles");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                mensajeError.postValue("Error de conexión: " + t.getLocalizedMessage());
            }
        });
    }
}
