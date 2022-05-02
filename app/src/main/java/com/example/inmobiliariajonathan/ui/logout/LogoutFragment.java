package com.example.inmobiliariajonathan.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inmobiliariajonathan.R;

public class LogoutFragment extends Fragment {

    private TextView tvLogout;

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        mostrarDialog();
        tvLogout = root.findViewById(R.id.tvLogout);
        tvLogout.setText("Este es el fragmento Logout");


        return root;
    }

    public void mostrarDialog(){
        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(this.getContext())
                .setTitle("Titulo")
                .setMessage("Desea cerrar la app")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);

                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.nav_inicio);

                    }
                });
        alertDialog.show();
    }
}