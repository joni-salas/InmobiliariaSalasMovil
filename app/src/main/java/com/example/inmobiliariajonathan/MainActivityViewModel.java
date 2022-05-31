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

    private ApiClient apiClient;
    private Context context;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        apiClient = new ApiClient();
        context = application.getApplicationContext(); // obtengo el contexto asi cuando extiende de AndroidViewModel
    }

    //setea en el Navigation el nombre y email del usuario logeado
    public void inicializarEncabezado(NavigationView navigationView){

        View header = navigationView.getHeaderView(0);
        //ImageView foto = header.findViewById(R.id.);
        TextView nombre = header.findViewById(R.id.NombreHeader);
        TextView email = header.findViewById(R.id.EmailHeader);

        Call<Propietario> propietarioCall = apiClient.getMyApiClient().getUsuarioActual(apiClient.getToken(context));

        propietarioCall.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()){
                    Propietario p = response.body();

                    nombre.setText(p.getNombre());
                    email.setText(p.getEmail());
                }else{
                    Toast.makeText(context,"PERFIL NOT SUCCESSFUL:",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(context,"ERROR CARGAR PERFIL: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }

}
