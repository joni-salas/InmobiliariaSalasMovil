package com.example.inmobiliariajonathan.ui.contratos;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.databinding.DetalleContratoFragmentBinding;
import com.example.inmobiliariajonathan.modelo.Contrato;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel mViewModel;
    private DetalleContratoFragmentBinding binding;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DetalleContratoFragmentBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        inicializar();
        return binding.getRoot();
    }

    private void inicializar() {

        // Observando los cambios del contrato
        mViewModel.getContrato().observe(getViewLifecycleOwner(), contrato -> {
            if (contrato != null) {
                binding.tvCodigoContrato.setText(String.valueOf(contrato.getIdContrato()));
                binding.tvFechaInicio.setText(contrato.getFechaInicio().toString());
                binding.tvFechaFinalizacion.setText(contrato.getFechaFin());
                binding.tvMontoDeAlquier.setText("$ " + contrato.getMontoAlquiler());
                binding.tvInquilinoContrato.setText(contrato.getInquilino().toString());
                binding.tvInmuebleContrato.setText(contrato.getInmueble().getDireccion());
            } else {
                binding.tvCodigoContrato.setText("");
                binding.tvFechaInicio.setText("");
                binding.tvFechaFinalizacion.setText("");
                binding.tvMontoDeAlquier.setText("$ ");
                binding.tvInquilinoContrato.setText("");
                binding.tvInmuebleContrato.setText("");
            }
        });

        // Botón Ingresar
        binding.btIngresar.setOnClickListener(v -> {
            Contrato con = new Contrato();
            String codigo = binding.tvCodigoContrato.getText().toString();
            con.setIdContrato(codigo.isEmpty() ? 0 : Integer.parseInt(codigo));

            Bundle bundle = new Bundle();
            bundle.putSerializable("contrato", con);
            Navigation.findNavController(v).navigate(R.id.pagosFragment, bundle);
        });

        // Cargar contrato desde argumentos
        mViewModel.cargarInmuebleAlquilados(getArguments());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
