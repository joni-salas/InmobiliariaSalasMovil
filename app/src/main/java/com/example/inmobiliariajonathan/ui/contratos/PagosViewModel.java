package com.example.inmobiliariajonathan.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariajonathan.modelo.Contrato;
import com.example.inmobiliariajonathan.modelo.Pago;
import com.example.inmobiliariajonathan.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Pago>> pagos = new MutableLiveData<>();
    private final ApiClient api;
    private final ApiClient.RetrofitService rfs;

    public PagosViewModel(@NonNull Application application) {
        super(application);
        api = new ApiClient();
        rfs = ApiClient.getMyApiClient();
    }

    public LiveData<List<Pago>> getPagos() {
        return pagos;
    }

    public void cargarPagosDeContratos(@NonNull Bundle bundle) {
        Contrato contrato = (Contrato) bundle.getSerializable("contrato");

        if (contrato == null) return;

        Call<List<Pago>> call = rfs.cargarPagos(contrato.getIdContrato(), api.getToken(getApplication()));
        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    pagos.postValue(response.body());
                } else {
                    Log.d("PagosViewModel", "Error onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Log.d("PagosViewModel", "Error onFailure: " + t.getLocalizedMessage());
            }
        });
    }

}
