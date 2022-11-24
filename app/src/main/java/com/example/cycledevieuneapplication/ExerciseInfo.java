package com.example.cycledevieuneapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExerciseInfo extends AppCompatActivity {
    ListView l;
    private EditText editTextReps;
    private EditText editTextPoids;
    private EditText editTextRecup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);

        Intent intent = getIntent();
        String exerciseName = intent.getStringExtra(userinfo.EXTRA_MESSAGE);

        getExerciseInfo(exerciseName);

        editTextReps = (EditText) findViewById(R.id.editTextReps);
        editTextPoids = (EditText) findViewById(R.id.editTextPoids);
        editTextRecup = (EditText) findViewById(R.id.editTextRecup);
    }

    public void getExerciseInfo(String exerciseName){

        //Get data from DB corresponding with the qrcode
        SQLiteManager db = new SQLiteManager(this);
        Exercises correspondingExercise = db.getExerciseByname(exerciseName);

        TextView nameView = findViewById(R.id.exerciseName);
        nameView.setText(correspondingExercise.getName());

        TextView descriptionView = findViewById(R.id.exerciseDescription);
        descriptionView.setText(correspondingExercise.getDescription());

        TextView musclesView = findViewById(R.id.exerciseMuscles);
        musclesView.setText(correspondingExercise.getMuscles());

        List<String> repsList = new ArrayList<>(Arrays.asList(correspondingExercise.getLastsWorkoutRepetitions().split(",")));
        l = (ListView) findViewById(R.id.listRepetitions);
        ArrayAdapter<String> arrayAdapterReps = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                repsList );

        l.setAdapter(arrayAdapterReps);

        List<String> poidsList = new ArrayList<>(Arrays.asList(correspondingExercise.getLastsWorkoutPoids().split(",")));
        l = (ListView) findViewById(R.id.listPoids);
        ArrayAdapter<String> arrayAdapterPoids = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                poidsList );

        l.setAdapter(arrayAdapterPoids);

        List<String> recupList = new ArrayList<>(Arrays.asList(correspondingExercise.getLastsWorkoutRecup().split(",")));
        l = (ListView) findViewById(R.id.listRecup);
        ArrayAdapter<String> arrayAdapterRecup = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                recupList );

        l.setAdapter(arrayAdapterRecup);

    }


    public void sendNewWorkout(View v){

       //prenom = editTextReps.getText().toString();
        //prenom = editTextPoids.getText().toString();
        //prenom = editTextRecup.getText().toString();
        if(editTextReps.getText().toString().matches("") || editTextPoids.getText().toString().matches("") || editTextRecup.getText().toString().matches("")){
            Log.d("empty", "Un truc est vide");
            Toast.makeText(ExerciseInfo.this, "Au moins un champ est vide, merci de tous les remplires", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("ok", "ok");
        }

    }
}
