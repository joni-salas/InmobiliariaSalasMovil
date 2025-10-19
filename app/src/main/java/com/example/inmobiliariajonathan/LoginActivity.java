package com.example.inmobiliariajonathan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.Toast;

import com.example.inmobiliariajonathan.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel lv;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lv = new ViewModelProvider(this).get(LoginViewModel.class);

        inicializarVista();
        observarViewModel();
    }

    private void inicializarVista() {
        binding.btLogin.setOnClickListener(v ->
                lv.iniciarSesion(
                        binding.etUsuario.getText().toString().trim(),
                        binding.etContrasena.getText().toString().trim()
                )
        );
    }

    private void observarViewModel() {
        lv.getMensaje().observe(this, mensaje ->
                Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_LONG).show()
        );
    }
}
