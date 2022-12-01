package com.example.cycledevieuneapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static java.lang.Math.min;


public class ExerciseInfo extends AppCompatActivity {
    ListView l;
    private EditText editTextReps;
    private EditText editTextPoids;
    private EditText editTextRecup;
    private Exercises correspondingExercise;
    SQLiteManager db;
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);

        Intent intent = getIntent();
        String exerciseName = intent.getStringExtra(userinfo.EXTRA_MESSAGE);

        db = new SQLiteManager(this);

        getExerciseInfo(exerciseName);

        editTextReps = (EditText) findViewById(R.id.editTextReps);
        editTextPoids = (EditText) findViewById(R.id.editTextPoids);
        editTextRecup = (EditText) findViewById(R.id.editTextRecup);

    }

    public void getExerciseInfo(String exerciseName){

        //Get data from DB corresponding with the qrcode

        correspondingExercise = db.getExerciseByname(exerciseName);

        TextView nameView = findViewById(R.id.exerciseName);
        nameView.setText(correspondingExercise.getName());

        mImageView = (ImageView) findViewById(R.id.imageView2);
        // ici il faudras enlever les espaces pour bien avoir le bon nom de fichier pour l'image
        int resID = getResources().getIdentifier(correspondingExercise.getName().toLowerCase() , "drawable", getPackageName());
        mImageView.setImageResource(resID);

        TextView dateView = findViewById(R.id.dateValue);
        dateView.setText(correspondingExercise.getLastsWorkoutDate());

        TextView descriptionView = findViewById(R.id.exerciseDescription);
        descriptionView.setText(correspondingExercise.getDescription());

        TextView musclesView = findViewById(R.id.exerciseMuscles);
        musclesView.setText(correspondingExercise.getMuscles());

        List<String> repsList = new ArrayList<>(Arrays.asList(correspondingExercise.getLastsWorkoutRepetitions().split(",")));
        Collections.reverse(repsList);
        repsList = repsList.subList(0, min(repsList.size(), 10));
        l = (ListView) findViewById(R.id.listRepetitions);
        ArrayAdapter<String> arrayAdapterReps = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                repsList );

        l.setAdapter(arrayAdapterReps);

        List<String> poidsList = new ArrayList<>(Arrays.asList(correspondingExercise.getLastsWorkoutPoids().split(",")));
        Collections.reverse(poidsList);
        poidsList = poidsList.subList(0, min(poidsList.size(), 10));

        l = (ListView) findViewById(R.id.listPoids);
        ArrayAdapter<String> arrayAdapterPoids = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                poidsList );

        l.setAdapter(arrayAdapterPoids);

        List<String> recupList = new ArrayList<>(Arrays.asList(correspondingExercise.getLastsWorkoutRecup().split(",")));
        Collections.reverse(recupList);
        recupList = recupList.subList(0, min(recupList.size(), 10));

        l = (ListView) findViewById(R.id.listRecup);
        ArrayAdapter<String> arrayAdapterRecup = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                recupList );

        l.setAdapter(arrayAdapterRecup);

    }


    public void sendNewWorkout(View v){
        if(editTextReps.getText().toString().matches("") || editTextPoids.getText().toString().matches("") || editTextRecup.getText().toString().matches("")){
            Toast.makeText(ExerciseInfo.this, "Au moins un champ est vide, merci de tous les remplires", Toast.LENGTH_SHORT).show();
        } else {
            correspondingExercise.setLastsWorkoutRepetitions(correspondingExercise.getLastsWorkoutRepetitions() + ", " + editTextReps.getText().toString());
            correspondingExercise.setLastsWorkoutPoids(correspondingExercise.getLastsWorkoutPoids() + ", " + editTextPoids.getText().toString());
            correspondingExercise.setLastsWorkoutRecup(correspondingExercise.getLastsWorkoutRecup() + ", " + editTextRecup.getText().toString());
            db.updateExerciseOnWorkoutUpdate(correspondingExercise);
            getExerciseInfo(correspondingExercise.getName());
            editTextReps.setText("");
            editTextPoids.setText("");
            editTextRecup.setText("");

        }
    }

    public void goUserinfo(View v){
        final Intent intentUserinfo = new Intent(this, userinfo.class);
        startActivity(intentUserinfo);
    }
}
