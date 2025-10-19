package com.example.inmobiliariajonathan.ui.inmuebles;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.modelo.Inmueble;
import com.example.inmobiliariajonathan.modelo.Propietario;
import com.example.inmobiliariajonathan.request.ApiClient;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearInmuebleViewModel extends AndroidViewModel {

    private ApiClient.RetrofitService rfs;
    private ApiClient api;
    private Context context;
    private MutableLiveData<Boolean> inmuebleCreadoMutable = new MutableLiveData<>();
    private MutableLiveData<String> mensajeMutable = new MutableLiveData<>();
    public LiveData<Boolean> getInmuebleCreadoMutable() {
        return inmuebleCreadoMutable;
    }
    public LiveData<String> getMensajeMutable() {
        return mensajeMutable;
    }


    public CrearInmuebleViewModel(@NonNull Application application) {
        super(application);

        rfs=ApiClient.getMyApiClient();
        context = application.getApplicationContext();
    }

    // CONVERTIR A BASE64 PARA LA API
    public String convertirImagen(ImageView imagen){
        String imgDeCode = "";
        BitmapDrawable drawable = (BitmapDrawable) imagen.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        if(bitmap!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            imgDeCode= Base64.encodeToString(b,Base64.DEFAULT);
        }
        return imgDeCode;
    }

    public void crearInmueble(Inmueble inmueble){
        api = new ApiClient();
        rfs = ApiClient.getMyApiClient();

        Call<Inmueble> crearInmueble = rfs.crearInmueble(inmueble, api.getToken(context));

        crearInmueble.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    mensajeMutable.postValue("Inmueble creado correctamente ✅");
                    inmuebleCreadoMutable.postValue(true);
                } else {
                    Log.d("Error OnResponse", response.message());
                    mensajeMutable.postValue("Error al crear el inmueble ❌");
                    inmuebleCreadoMutable.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.d("Error onFailure", t.getLocalizedMessage());
                inmuebleCreadoMutable.postValue(false);
                mensajeMutable.postValue("Fallo la conexión con el servidor");
            }
        });
    }






}