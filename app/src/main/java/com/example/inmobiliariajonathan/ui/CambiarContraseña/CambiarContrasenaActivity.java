package com.example.inmobiliariajonathan.ui.CambiarContraseña;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.inmobiliariajonathan.LoginActivity;
import com.example.inmobiliariajonathan.databinding.ActivityCambiarContraBinding;


public class CambiarContrasenaActivity extends AppCompatActivity {

    private ActivityCambiarContraBinding binding;
    private CambiarContrasenaViewModel cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CambiarContrasenaViewModel.class);

        binding = ActivityCambiarContraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cambiarcontraseña();

        cv.getMensaje().observe(this, mensaje -> {
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        });

        cv.getCambioExitoso().observe(this, exito -> {
            if (Boolean.TRUE.equals(exito)) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void cambiarcontraseña(){
        binding.btCambiarContra.setOnClickListener(view -> {
            cv.cambiarcontra(binding.etemail.getText().toString(), binding.etContranueva.getText().toString(), binding.etRepContranueva.getText().toString());
        });
    }
}