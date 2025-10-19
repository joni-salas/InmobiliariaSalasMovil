package com.example.inmobiliariajonathan;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariajonathan.modelo.Propietario;
import com.example.inmobiliariajonathan.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private final Context context;
    private final ApiClient.RetrofitService rfs;

    private final MutableLiveData<String> mensajeMD = new MutableLiveData<>();
    private final MutableLiveData<String> tokenMD = new MutableLiveData<>();
    private static String token;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        rfs = ApiClient.getMyApiClient();
    }

    public LiveData<String> getMensaje() {
        return mensajeMD;
    }

    public LiveData<String> getTokenMD() {
        return tokenMD;
    }

    /**
     * Inicia sesión y guarda el token en SharedPreferences
     */
    public void iniciarSesion(String usuario, String clave) {
        Call<String> login = rfs.login(usuario, clave);

        login.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    token = response.body();
                    Log.d("token", token);

                    // Guardo el token en SharedPreferences
                    SharedPreferences sp = context.getSharedPreferences("datos.dat", Context.MODE_PRIVATE);
                    sp.edit().putString("token", "Bearer " + token).apply();

                    mensajeMD.postValue("Bienvenido " + usuario);
                    tokenMD.postValue(token);

                    // Abrir MainActivity desde ViewModel (no ideal, pero posible)
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else {
                    Log.d("Error", response.message());
                    mensajeMD.postValue("Contraseña o Usuario incorrecto");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error on Failure= ", t.getLocalizedMessage());
                mensajeMD.postValue("Error de conexión: " + t.getLocalizedMessage());
            }
        });
    }
}
