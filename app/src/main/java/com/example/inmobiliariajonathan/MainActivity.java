package com.example.inmobiliariajonathan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inmobiliariajonathan.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private GoogleMap mapa;
    private LatLng SANLUIS = new LatLng(-33.280576, -66.332482);

    private SensorManager sensorManager;
    private LeeSensor leeSensor;
    private int bandera = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
        }

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_perfil, R.id.nav_inmueble , R.id.nav_inquilino, R.id.nav_contrato, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setItemIconTintList(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();

        leeSensor = new LeeSensor();
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        List<Sensor> listaSensores = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        for(Sensor ele:listaSensores){
            sensorManager.registerListener(leeSensor,ele,SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(leeSensor);
    }

    private class LeeSensor implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(bandera == 1 && (sensorEvent.values[0] >= 20 || sensorEvent.values[0] <= -20 ) ){
                Uri tel = Uri.parse("tel:2664687890");
                startActivity(new Intent(Intent.ACTION_CALL, tel));
                bandera = 0;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

}