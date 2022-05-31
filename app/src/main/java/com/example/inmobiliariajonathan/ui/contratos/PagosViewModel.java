package com.example.inmobiliariajonathan.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariajonathan.modelo.Contrato;
import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.modelo.Pago;
import com.example.inmobiliariajonathan.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pago>> pagos;
    private Context context;
    private ApiClient api;
    private ApiClient.RetrofitService rfs;

    public PagosViewModel(@NonNull Application application) {
        super(application);
        context = application.getBaseContext();
        api = new ApiClient();
        rfs=ApiClient.getMyApiClient();
    }


    public LiveData<List<Pago>> getPagos() {
        if (pagos == null) {
            pagos = new MutableLiveData<>();
        }
        return pagos;
    }

    public void cargarPagosDeContratos(Bundle bundle) {
        Contrato pagosLista  = (Contrato) bundle.getSerializable("contrato");

        Call<List<Pago>> pagosxContrato = rfs.cargarPagos(pagosLista.getIdContrato(),api.getToken(context));

        pagosxContrato.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if(response.isSuccessful()){
                    pagos.setValue(response.body());

                }else {
                    Log.d("Error onresponse: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Log.d("Error onFailure: ", t.getLocalizedMessage());
            }
        });


    }
}