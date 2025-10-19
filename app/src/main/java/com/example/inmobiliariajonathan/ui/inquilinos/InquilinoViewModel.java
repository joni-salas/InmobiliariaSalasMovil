package com.example.inmobiliariajonathan.ui.inquilinos;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;

import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.modelo.Inquilino;
import com.example.inmobiliariajonathan.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends AndroidViewModel {

    private MutableLiveData<Inquilino> inquilino;
    private MutableLiveData<String> mensajeMutable;
    private ApiClient.RetrofitService rfs;
    private ApiClient api;

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
        rfs = ApiClient.getMyApiClient();
        api = new ApiClient();
        inquilino = new MutableLiveData<>();
        mensajeMutable = new MutableLiveData<>();
    }

    public LiveData<Inquilino> getInquilino() {
        return inquilino;
    }

    public LiveData<String> getMensajeMutable() {
        return mensajeMutable;
    }

    public void cargarInquilino(Bundle bundle){
        if(bundle == null) {
            mensajeMutable.postValue("Bundle es null");
            return;
        }

        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");  //tag que viene del adapter
        if(inmueble == null){
            mensajeMutable.postValue("Inmueble es null");
            return;
        }

        Call<Inquilino> call = api.getMyApiClient().cargarInquilino(inmueble.getIdInmueble(), api.getToken(getApplication().getApplicationContext()));

        call.enqueue(new Callback<Inquilino>() {
            @Override
            public void onResponse(Call<Inquilino> call, Response<Inquilino> response) {
                if(response.isSuccessful() && response.body() != null){
                    inquilino.postValue(response.body());
                } else {
                    Log.d("Error onResponse", response.message());
                    mensajeMutable.postValue("No posee inquilino");
                }
            }

            @Override
            public void onFailure(Call<Inquilino> call, Throwable t) {
                Log.d("Error onFailure", t.getLocalizedMessage());
                mensajeMutable.postValue("Error al cargar inquilino");
            }
        });
    }
}
