package com.example.inmobiliariajonathan.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariajonathan.modelo.Contrato;
import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> inmuebles;
    private Context context;
    private ApiClient.RetrofitService rfs;
    ApiClient api;

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        rfs=ApiClient.getMyApiClient();
        api= new ApiClient();
    }

    public LiveData<List<Inmueble>> getInmuebles() {
        if (inmuebles == null) {
            inmuebles = new MutableLiveData<>();
        }
        return inmuebles;
    }

    public void cargarInmueblesAlquilados() {

        Call<List<Inmueble>> listaInmuebles = rfs.inmueblesPorUsuario(api.getToken(context));

        listaInmuebles.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    inmuebles.setValue(response.body());
                }else {
                    Toast.makeText(context,"Error al listar inmuebles", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {

            }
        });



    }
}