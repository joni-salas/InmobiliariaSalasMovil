package com.example.inmobiliariajonathan;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariajonathan.modelo.Propietario;
import com.example.inmobiliariajonathan.request.ApiClient;
import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> mensajeMD;
    private MutableLiveData<String> tokenMD;
    private ApiClient.RetrofitService rfs;
    private static String token;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        rfs=ApiClient.getMyApiClient();
    }

    public LiveData<String> getMensaje(){
        if(mensajeMD ==null){
            mensajeMD = new MutableLiveData<>();
        }
        return mensajeMD;
    }
    public LiveData<String> getTokenMD(){
        if(tokenMD ==null){
            tokenMD = new MutableLiveData<>();
        }
        return tokenMD;
    }

    public void iniciarSesion(String usuario, String clave){

        Call<String> login=rfs.login(usuario, clave);

        login.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    token = response.body();
                    Log.d("token", response.body());

                    // Guardo el token en SharedPreferences
                    SharedPreferences sp = context.getSharedPreferences("datos.dat",0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("token", "Bearer " + token);
                    editor.commit();
                    mensajeMD.setValue("Bienvenido " + usuario);

                    //Abro el intent Con vista principal
                    Intent i =new Intent(context,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);        //asi se llama activity cuando no esta en una activitty
                    context.startActivity(i);

                }else{
                    Log.d("Error", response.message());
                    mensajeMD.setValue("Contraseña o Usuario incorrecto");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error on Failure= ", t.getLocalizedMessage());
            }
        });

    }


}
