package com.example.inmobiliariajonathan;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariajonathan.modelo.Propietario;
import com.example.inmobiliariajonathan.request.ApiClient;

public class LoginViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> mensaje;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<String> getMensaje(){
        if(mensaje ==null){
            mensaje = new MutableLiveData<>();
        }
        return mensaje;
    }

    public void iniciarSesion(String usuario, String contraseña){
        ApiClient api = ApiClient.getApi();
        Propietario propietarioLogeado = api.login(usuario, contraseña);
        if(propietarioLogeado !=null){

            Intent i =new Intent(context,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }else{
            mensaje.setValue("Usuario y/o Contrasela incorrecto");
        }

    }


}
