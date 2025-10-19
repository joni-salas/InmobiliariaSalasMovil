package com.example.inmobiliariajonathan.ui.logout;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;
    private LogoutViewModel logoutViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLogoutBinding.inflate(inflater, container, false);

        logoutViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);

        binding.tvLogout.setText("Este es el fragmento Logout");

        mostrarDialog();

        logoutViewModel.getCerrarApp().observe(getViewLifecycleOwner(), cerrar -> {
            if (cerrar != null && cerrar) {
                System.exit(0);
            }
        });

        logoutViewModel.getNavegarInicio().observe(getViewLifecycleOwner(), navegar -> {
            if (navegar != null && navegar) {
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.nav_inicio);
            }
        });

        return binding.getRoot();
    }

    private void mostrarDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext())
                .setTitle("Titulo")
                .setMessage("Desea cerrar la app?")
                .setPositiveButton("Sí", (dialog, which) -> logoutViewModel.cerrarSesion())
                .setNegativeButton("No", (dialog, which) -> logoutViewModel.cancelarLogout());

        alertDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
