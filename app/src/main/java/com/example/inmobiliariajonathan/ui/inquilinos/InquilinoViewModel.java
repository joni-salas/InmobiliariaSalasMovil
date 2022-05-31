package com.example.inmobiliariajonathan.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.modelo.Inquilino;
import com.example.inmobiliariajonathan.request.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends AndroidViewModel {
    private MutableLiveData<Inquilino> inquilino;
    private Context context;
    private ApiClient.RetrofitService rfs;
    ApiClient api;

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        rfs = ApiClient.getMyApiClient();
        api = new ApiClient();
    }


    public LiveData<Inquilino> getInquilino(){
        if(inquilino == null){
            inquilino = new MutableLiveData<>();
        }
        return inquilino;

    }
    public void cargarInquilino(Bundle bundle){
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble") ;  //este tag es el que viene de inquilino adapter

        Call<Inquilino> inquilinoxinmueble = api.getMyApiClient().cargarInquilino(inmueble.getIdInmueble(),api.getToken(context));

        inquilinoxinmueble.enqueue(new Callback<Inquilino>() {
            @Override
            public void onResponse(Call<Inquilino> call, Response<Inquilino> response) {
                if(response.isSuccessful()){
                    inquilino.setValue(response.body());
                }else{
                    Log.d("Error onResponse", response.message());
                }
            }

            @Override
            public void onFailure(Call<Inquilino> call, Throwable t) {

            }
        });


    }
}