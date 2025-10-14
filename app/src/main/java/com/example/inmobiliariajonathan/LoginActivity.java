package com.example.inmobiliariajonathan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.inmobiliariajonathan.databinding.ActivityLoginBinding;
import com.example.inmobiliariajonathan.ui.CambiarContraseña.CambiarContrasenaActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel lv;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);

        binding= ActivityLoginBinding.inflate(getLayoutInflater());//inflar el layout
        setContentView(binding.getRoot());//vista del binding como contenido de la Activity

        inicializarVista();

        lv.getMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(LoginActivity.this,s,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void inicializarVista(){

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.iniciarSesion(binding.etUsuario.getText().toString().trim(),binding.etContrasena.getText().toString().trim());
            }
        });

        binding.btOlvidecontrasena.setOnClickListener(view -> {

            Intent intent = new Intent(this, CambiarContrasenaActivity.class);
            startActivity(intent);
        });


    }


}