package com.example.inmobiliariajonathan.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariajonathan.modelo.Propietario;
import com.example.inmobiliariajonathan.request.ApiClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Boolean> estadoMT;
    private MutableLiveData<String> textoBoton;
    private MutableLiveData<Propietario> usuarioMT;
    private ApiClient.RetrofitService rfs;
    private ApiClient api;
    private Context context;



    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el perfil");
        rfs=ApiClient.getMyApiClient();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Boolean> getEstadoMutable(){
        if(estadoMT==null){
            estadoMT=new MutableLiveData<>();
        }
        return estadoMT;
    }

    public LiveData<String> getTextoBoton(){
        if(textoBoton==null){
            textoBoton=new MutableLiveData<>();
        }
        return textoBoton;
    }
    public LiveData<Propietario> getUsuarioMutable(){
        if(usuarioMT==null){
            usuarioMT=new MutableLiveData<>();
        }
        return usuarioMT;
    }

    public void accionBoton(String txtBoton,Propietario propietario){

        if(txtBoton.equals("Editar")){
            textoBoton.setValue("Guardar");
            estadoMT.setValue(true);
        }else{
            textoBoton.setValue("Editar");
            estadoMT.setValue(false);
            //aca llamo al actualizarUSuario
            actualizarUsuario(propietario);
        }
    }

    public void traerDatos(){
        api = new ApiClient();
        rfs=ApiClient.getMyApiClient();
        Call<Propietario> usuarioActual=rfs.getUsuarioActual(api.getToken(context));

        usuarioActual.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    Log.d("Propietario: ", response.body().getNombre());
                    usuarioMT.setValue(response.body());  //seteo el el Propietario en el mutable usuario

                }else{
                    Log.d("Propietario: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("Error onFailure: ", t.getLocalizedMessage());
            }
        });

    }

    public void actualizarUsuario(Propietario propietario){
        api = new ApiClient();
        rfs = ApiClient.getMyApiClient();

        Log.d("PUT_JSON", new Gson().toJson(propietario));
        Call<Propietario> usuarioActual = rfs.editarPerfil(propietario, api.getToken(context));

        usuarioActual.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()){
                    usuarioMT.setValue(response.body());
                    Toast.makeText(context, "Perfil actualizado correctamente", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("Retrofit Msg:", response.message());

                    if (response.errorBody() != null) {
                        try {
                            String errorBodyDetail = response.errorBody().string();

                            Log.e("Error Detalle:", errorBodyDetail);
                            Toast.makeText(context, "Error Servidor: " + errorBodyDetail, Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            Log.e("Error Lectura Body:", "Fallo al leer el detalle del error: " + e.getLocalizedMessage());
                            Toast.makeText(context, "Error al actualizar Perfil (Sin detalle)", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Error al actualizar Perfil", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("Error onFailure", t.getLocalizedMessage());
            }
        });
    }
}