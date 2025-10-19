package com.example.inmobiliariajonathan.ui.logout;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LogoutViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> cerrarApp = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navegarInicio = new MutableLiveData<>();

    private final Context context;

    public LogoutViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Boolean> getCerrarApp() {
        return cerrarApp;
    }

    public LiveData<Boolean> getNavegarInicio() {
        return navegarInicio;
    }

    public void cerrarSesion() {
        SharedPreferences.Editor editor = context.getSharedPreferences("datos.dat", Context.MODE_PRIVATE).edit();
        editor.clear().apply();

        cerrarApp.setValue(true);
    }
    public void cancelarLogout() {
        navegarInicio.setValue(true);
    }
}
