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

    private static final String URLBASE="http://192.168.0.172:45455/api/";
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

        @PUT("Propietarios/actualizar")
        public Call<Propietario> editarPerfil(@Body Propietario propietario,@Header("Authorization") String token);

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







    private ArrayList<Propietario> propietarios=new ArrayList<>();
    private ArrayList<Inquilino> inquilinos=new ArrayList<>();
    private ArrayList<Inmueble> inmuebles=new ArrayList<>();
    private ArrayList<Contrato> contratos=new ArrayList<>();
    private ArrayList<Pago> pagos=new ArrayList<>();
    private static Propietario usuarioActual=null;
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




    //Servicios
    //Para que pueda iniciar sesion
    public Propietario login(String mail, final String password){
        for(Propietario propietario:propietarios){
            if(propietario.getEmail().equals(mail)&&propietario.getContraseña().equals(password)){
                usuarioActual=propietario;
                return propietario;
            }
        }
        return null;
    }


    //Retorna el usuario que inició Sesión
    public Propietario obtenerUsuarioActual(){
        return usuarioActual;
    }

    //Retorna las propiedades del usuario propietario logueado
    public ArrayList<Inmueble> obtnerPropiedades(){
        ArrayList<Inmueble> temp=new ArrayList<>();
        for(Inmueble inmueble:inmuebles){
            if(inmueble.getPropietario().equals(usuarioActual)){
                temp.add(inmueble);
            }
        }
        return temp;
    }

    //Lista de inmuebles con contratos vigentes del Propietario logueado
    public ArrayList<Inmueble> obtenerPropiedadesAlquiladas(){
        ArrayList<Inmueble> temp=new ArrayList<>();
        for(Contrato contrato:contratos){
            if(contrato.getInmueble().getPropietario().equals(usuarioActual)){
                temp.add(contrato.getInmueble());
            }
        }
        return temp;
    }


//Dado un inmueble retorna el contrato activo de dicho inmueble

    public Contrato obtenerContratoVigente(Inmueble inmueble){

        for(Contrato contrato:contratos){
            if(contrato.getInmueble().equals(inmueble)){
                return contrato;
            }
        }
        return null;
    }

    //Dado un inmueble, retorna el inquilino del ultimo contrato activo de ese inmueble.
    public Inquilino obtenerInquilino(Inmueble inmueble){
        for(Contrato contrato:contratos){
            if(contrato.getInmueble().equals(inmueble)){
                return contrato.getInquilino();
            }
        }
        return null;
    }
    //Dado un Contrato, retorna los pagos de dicho contrato
    public ArrayList<Pago> obtenerPagos(Contrato contratoVer){
        ArrayList<Pago> temp=new ArrayList<>();
        for(Contrato contrato:contratos){
            if(contrato.equals(contratoVer)){
                for(Pago pago:pagos){
                    if(pago.getContrato().equals(contrato)){
                        temp.add(pago);
                    }
                }
            }
            break;
        }
        return temp;
    }
    //Actualizar Perfil
    public void actualizarPerfil(Propietario propietario){
        usuarioActual.setId(propietario.getId());
        usuarioActual.setDni(propietario.getDni());
        usuarioActual.setApellido(propietario.getApellido());
        usuarioActual.setNombre(propietario.getNombre());
        usuarioActual.setEmail(propietario.getEmail());
        usuarioActual.setContraseña(propietario.getContraseña());
        usuarioActual.setTelefono(propietario.getTelefono());

    }

    //ActualizarInmueble
    public void actualizarInmueble(Inmueble inmueble){
        int posicion=inmuebles.indexOf(inmueble);
        if(posicion!=-1){
            inmuebles.set(posicion,inmueble);
        }
    }

    private void cargaDatos(){

        //Propietarios
        Propietario juan=new Propietario(1,"23492012L","Juan","Perez","juan@mail.com","123","2664553447",true);
        Propietario sonia=new Propietario(2,"17495869L","Sonia","Lucero","sonia@mail.com","123","266485417",true);
        propietarios.add(juan);
        propietarios.add(sonia);

        //Inquilinos
        //Inquilino mario=new Inquilino(100,25340691L,"Mario","Luna","Aiello sup.","luna@mail.com","2664253411","Lucero Roberto","2664851422");
        //inquilinos.add(mario);

        //Inmuebles
        Inmueble salon=new Inmueble(501,"Colon 340","salon","2","20000",juan,"1","http://www.secsanluis.com.ar/servicios/salon1.jpg","54","");
        Inmueble casa=new Inmueble(502,"Mitre 800","casa","2","15000",juan,"1","http://www.secsanluis.com.ar/servicios/casa1.jpg","45","");
        Inmueble otraCasa=new Inmueble(503,"Salta 325","casa","3","17000",sonia,"1","http://www.secsanluis.com.ar/servicios/casa2.jpg","54","");
        Inmueble dpto=new Inmueble(504,"Lavalle 450","dpto","2","25000",sonia,"1","http://www.secsanluis.com.ar/servicios/departamento1.jpg","54","");
        Inmueble casita=new Inmueble(505,"Belgrano 218","casa","5","90000",sonia,"1","http://www.secsanluis.com.ar/servicios/casa3.jpg","54","");

        inmuebles.add(salon);
        inmuebles.add(casa);
        inmuebles.add(otraCasa);
        inmuebles.add(dpto);
        inmuebles.add(casita);

        //Contratos
        //Contrato uno=new Contrato(701, "05/01/2020","05/01/2020","17000",mario,otraCasa);
        //contratos.add(uno);
        //Pagos
        //pagos.add(new Pago(900,1,uno,17000,"10/02/2020"));
       // pagos.add(new Pago(901,2,uno,17000,"10/03/2020"));
        //pagos.add(new Pago(902,3,uno,17000,"10/04/2020"));




    }

}