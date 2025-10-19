package com.example.inmobiliariajonathan.ui.CambiarContraseña;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariajonathan.modelo.CambioClave;
import com.example.inmobiliariajonathan.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarContrasenaViewModel extends AndroidViewModel {

    private final Context context;
    private final ApiClient.RetrofitService rfs;
    private final ApiClient api;
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cambioExitoso = new MutableLiveData<>();

    public CambiarContrasenaViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        this.api = new ApiClient();
        this.rfs = ApiClient.getMyApiClient();
    }

    public MutableLiveData<String> getMensaje() {
        return mensaje;
    }

    public MutableLiveData<Boolean> getCambioExitoso() {
        return cambioExitoso;
    }

    public void cambiarcontra(String email, String contraNueva, String repContraNueva) {
        if (email.isEmpty() || contraNueva.isEmpty() || repContraNueva.isEmpty()) {
            mensaje.postValue("Todos los campos son obligatorios");
            return;
        }

        if (!contraNueva.equals(repContraNueva)) {
            mensaje.postValue("Las contraseñas no coinciden");
            return;
        }

        CambioClave cambio = new CambioClave(email, contraNueva, repContraNueva);
        Call<Void> cambioContra = rfs.cambiarClave(cambio, api.getToken(context));

        cambioContra.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() || response.code() == 204) {
                    mensaje.postValue("Contraseña actualizada correctamente");
                    cambioExitoso.postValue(true);
                } else {
                    mensaje.postValue("Error al cambiar la contraseña. Código: " + response.code());
                    cambioExitoso.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //mensaje.setValue("Error de conexión: " + t.getMessage());
                mensaje.postValue("Error de conexión: " + t.getMessage());
                cambioExitoso.setValue(false);
            }
        });
    }
}
