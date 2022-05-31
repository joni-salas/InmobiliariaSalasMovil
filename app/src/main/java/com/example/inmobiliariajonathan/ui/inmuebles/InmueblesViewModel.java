package com.example.inmobiliariajonathan.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> inmuebles;
    private Context context;
    private ApiClient.RetrofitService rfs;
    ApiClient api;

    public InmueblesViewModel(@NonNull Application application) {
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

    public void cargarInmuebles() {
        //Acá traemos todos los inmuebles de la base de datos
        Call<List<Inmueble>> listaInmuebles = rfs.inmueblesPorUsuario(api.getToken(context));

        listaInmuebles.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){

                    response.body().forEach(e -> { Log.d("listaInmuebles: ", e.getImagen()); } );
                    inmuebles.setValue(response.body());

                }else{
                    Log.d("Error onResponse", response.message());
                    Toast.makeText(context,"Error al listar inmuebles", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.d("Error On Failure: ", t.getLocalizedMessage());
                Toast.makeText(context,"Error al listar inmuebles", Toast.LENGTH_LONG).show();
            }
        });


    }
}