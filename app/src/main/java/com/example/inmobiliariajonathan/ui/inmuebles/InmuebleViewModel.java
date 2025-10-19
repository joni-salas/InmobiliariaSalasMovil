package com.example.inmobiliariajonathan.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.modelo.Propietario;
import com.example.inmobiliariajonathan.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmueble;
    private MutableLiveData<String> textoBoton;
    private ApiClient apiClient;
    private Context context;

    public InmuebleViewModel(@NonNull Application application) {
        super(application);
        apiClient= new ApiClient();
        context = application.getApplicationContext();
    }


    public LiveData<Inmueble> getInmueble(){
        if(inmueble == null){
            inmueble = new MutableLiveData<>();
        }
        return inmueble;
    }

    public LiveData<String> getTextoBoton(){
        if(textoBoton==null){
            textoBoton=new MutableLiveData<>();
        }
        return textoBoton;
    }

    public void accionBoton(String txtBoton,Bundle bundle){

        if(txtBoton.equals("Dar de baja")){
            textoBoton.postValue("Dar de alta");
            this.darDeBajaInmeuble(bundle);
        }else{
            textoBoton.postValue("Dar de baja");

            this.darDeBajaInmeuble(bundle);
        }
    }

    public void cargarInmueble(Bundle bundle){
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble") ;  //este tag es el que viene de inmueble adapter
        this.inmueble.postValue(inmueble);
    }

    public void darDeBajaInmeuble(Bundle bundle){

        Inmueble inmuebleBaja = (Inmueble) bundle.getSerializable("inmueble") ;  //este tag es el que viene de inmueble adapter
        Log.d("Inmueble id: ", inmuebleBaja.getIdInmueble()+"");
        Call<Inmueble> bajaInmueble = apiClient.getMyApiClient().bajaInmueble(inmuebleBaja.getIdInmueble(),apiClient.getToken(context));

        bajaInmueble.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    if(response.body().isEstado().equals("1")){
                        Toast.makeText(context,"Inmueble dado de Alta",Toast.LENGTH_LONG).show();
                        inmueble.postValue(response.body()); // seteo el inmueble actualizado al mutable
                    }else{
                        Toast.makeText(context,"Inmueble dado de Baja",Toast.LENGTH_LONG).show();
                        inmueble.postValue(response.body()); // seteo el inmueble actualizado al mutable
                    }

                }else{
                    Toast.makeText(context,"Error al Actualizar",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.d("Error onFailure", t.getLocalizedMessage());
            }
        });
    }

}