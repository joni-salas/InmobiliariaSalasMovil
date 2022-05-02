package com.example.inmobiliariajonathan.ui.perfil;

import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariajonathan.modelo.Propietario;
import com.example.inmobiliariajonathan.request.ApiClient;

public class PerfilViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Boolean> estado;
    private MutableLiveData<String> textoBoton;
    private MutableLiveData<Propietario> usuario;
    private ApiClient apiClient;


    public PerfilViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el perfil");
        apiClient=ApiClient.getApi();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Boolean> getEstado(){
        if(estado==null){
            estado=new MutableLiveData<>();
        }
        return estado;
    }

    public LiveData<String> getTextoBoton(){
        if(textoBoton==null){
            textoBoton=new MutableLiveData<>();
        }
        return textoBoton;
    }
    public LiveData<Propietario> getUsuario(){
        if(usuario==null){
            usuario=new MutableLiveData<>();
        }
        return usuario;
    }

    public void accionBoton(String txtBoton,Propietario propietario){

        if(txtBoton.equals("Editar")){
            textoBoton.setValue("Guardar");
            estado.setValue(true);
        }else{
            textoBoton.setValue("Editar");
            estado.setValue(false);

            apiClient.actualizarPerfil(propietario);
        }
    }

    public void traerDatos(){


        usuario.setValue(apiClient.obtenerUsuarioActual());

    }






}