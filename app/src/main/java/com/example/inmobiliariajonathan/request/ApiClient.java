package com.example.inmobiliariajonathan.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.inmobiliariajonathan.modelo.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class ApiClient {

    private static final String URLBASE="http://192.168.0.112:45455/api/";
    private static RetrofitService myApiInteface;


    public String getToken(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("datos.dat", 0);
        String token = sharedPreferences.getString("token", "Error al buscar token");

        return token;
    }


    public static RetrofitService getMyApiClient(){

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        myApiInteface=retrofit.create(RetrofitService.class);

        return myApiInteface;
    }


    public interface RetrofitService{
        //PROPIETARIO
        @FormUrlEncoded
        @POST("Propietarios/login")
        Call<String> login (@Field("Usuario")String usuario, @Field("Clave")String clave);

        @GET("Propietarios")
        public Call<Propietario> getUsuarioActual(@Header("Authorization") String token);

        @PUT("Propietarios/actualizarPerfil")
        public Call<Propietario> editarPerfil(@Body Propietario propietario,@Header("Authorization") String token);

        @PUT("Propietarios/actualizarClave")
        public Call<Void> cambiarClave(@Body CambioClave cambioClave, @Header("Authorization") String token);

        //INMUEBLE
        @GET("Inmuebles/obtener")
        public Call<List<Inmueble>> inmueblesPorUsuario(@Header("Authorization") String token);

        @PUT("Inmuebles/{id}")
        Call<Inmueble>bajaInmueble(@Path("id") int id, @Header("Authorization") String token);

        @POST("Inmuebles/crear")
        Call<Inmueble>crearInmueble(@Body Inmueble inmueble, @Header("Authorization") String token);

        //CONTRATO

        @GET("Contratos/{id}")
        Call<Contrato>cargarContrato(@Path("id") int id, @Header("Authorization") String token);

        //Pago
        @GET("Pagos/{id}")
        Call<List<Pago>>cargarPagos(@Path("id") int id, @Header("Authorization") String token);

        //INQUILINO
        @GET("Inquilinos/{id}")
        Call<Inquilino>cargarInquilino(@Path("id") int id, @Header("Authorization") String token);

    }


    private static ApiClient api=null;

    public ApiClient(){
        //Nos conectamos a nuestra "Base de Datos"
        //cargaDatos();
    }
    //Método para crear una instancia de ApiClient
    public static ApiClient getApi(){
        if (api==null){
            api=new ApiClient();
        }
        return api;

    }


}