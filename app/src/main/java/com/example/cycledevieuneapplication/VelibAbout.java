package com.example.cycledevieuneapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class VelibAbout extends AppCompatActivity {
    private String velibInformations = "Vélib' Métropole, anciennement Vélib', est un système de vélos en libre-service disponible à Paris et dans les autres villes membres du syndicat Autolib' et Vélib' Métropole ; il s'inscrit dans le domaine de la mobilité partagée. Par synecdoque référentielle, « vélib' » désigne aussi la bicyclette.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_velib_about);

        TextView textView = findViewById(R.id.velibInfo);
        textView.setText(velibInformations);

    }
}
