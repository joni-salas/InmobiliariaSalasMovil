package com.example.inmobiliariajonathan.ui.logout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.inmobiliariajonathan.R;

public class LogoutViewModel extends AndroidViewModel {
    private MutableLiveData<AlertDialog.Builder> mutableDialogBuilder;

    private Context context;
    private Application application;
    private SharedPreferences getSharedPreferences;

    public LogoutViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        this.application = application;
        mostrarDialog();
    }

    public LiveData<AlertDialog.Builder> getMutableDialogBuilder() {
        if(mutableDialogBuilder == null){
            mutableDialogBuilder = new MutableLiveData<>();
        }
        return mutableDialogBuilder;
    }

    public void mostrarDialog(){
        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(application)
                .setTitle("Titulo")
                .setMessage("Desea cerrar la app")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Elimino los datos de shared preferends (TOKEN) al cerrar session
                        SharedPreferences.Editor editor = context.getSharedPreferences("datos.dat",0).edit();
                        editor.clear().apply();
                        System.exit(0);

                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main).navigate(R.id.nav_inicio);

                    }
                });
        mutableDialogBuilder.setValue(alertDialog);
    }
    // TODO: Implement the ViewModel
}