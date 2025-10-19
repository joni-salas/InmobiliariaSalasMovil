package com.example.inmobiliariajonathan;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.inmobiliariajonathan.modelo.Propietario;
import com.example.inmobiliariajonathan.request.ApiClient;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    private final ApiClient apiClient;
    private final Context context;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        apiClient = new ApiClient();
        context = application.getApplicationContext();
    }

    /**
     * Inicializa el header del NavigationView con nombre y email del usuario logueado
     */
    public void inicializarEncabezado(NavigationView navigationView) {
        // Obtener el header como View
        View header = navigationView.getHeaderView(0);

        // Hacer cast seguro a TextView
        TextView tvNombre = header.findViewById(R.id.NombreHeader);
        TextView tvEmail = header.findViewById(R.id.EmailHeader);

        // Llamada a API para obtener usuario actual
        Call<Propietario> call = apiClient.getMyApiClient().getUsuarioActual(apiClient.getToken(context));
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Propietario propietario = response.body();
                    tvNombre.setText(propietario.getNombre());
                    tvEmail.setText(propietario.getEmail());
                } else {
                    Toast.makeText(context, "Error al cargar perfil", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(context, "Error al cargar perfil: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
