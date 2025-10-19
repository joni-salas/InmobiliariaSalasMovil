package com.example.inmobiliariajonathan.ui.inquilinos;

import android.app.Application;
import android.util.Log;

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

public class InquilinosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> inmuebles;
    private MutableLiveData<String> mensajeMutable;

    private ApiClient.RetrofitService rfs;
    private ApiClient api;

    public InquilinosViewModel(@NonNull Application application) {
        super(application);
        rfs = ApiClient.getMyApiClient();
        api = new ApiClient();
        inmuebles = new MutableLiveData<>();
        mensajeMutable = new MutableLiveData<>();
    }

    public LiveData<List<Inmueble>> getInmuebles() {
        return inmuebles;
    }

    public LiveData<String> getMensajeMutable() {
        return mensajeMutable;
    }

    public void cargarInmuebles() {
        Call<List<Inmueble>> listaInmuebles = rfs.inmueblesPorUsuario(api.getToken(getApplication().getApplicationContext()));

        listaInmuebles.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    List<Inmueble> lista = response.body();
                    if (lista != null) {
                        lista.forEach(e -> Log.d("listaInmuebles: ", e.getImagen()));
                        inmuebles.postValue(lista);
                    }
                } else {
                    Log.d("Error onResponse", response.message());
                    mensajeMutable.postValue("Error al listar inmuebles");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.d("Error On Failure: ", t.getLocalizedMessage());
                mensajeMutable.postValue("Error al listar inmuebles");
            }
        });
    }
}
