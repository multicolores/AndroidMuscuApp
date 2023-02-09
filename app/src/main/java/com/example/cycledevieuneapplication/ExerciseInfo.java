package com.example.cycledevieuneapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    private EditText editTextRecup;
    private Exercises correspondingExercise;
    SQLiteManager db;
    private ImageView mImageView;

    private int poidsValue;
    private int recupValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);

        Intent intent = getIntent();
        String exerciseName = intent.getStringExtra(ExercisesList.EXTRA_MESSAGE);

        db = new SQLiteManager(this);

        getExerciseInfo(exerciseName);

        editTextReps = (EditText) findViewById(R.id.editTextReps);
        editTextRecup = (EditText) findViewById(R.id.editTextRecup);
    }

    public void getExerciseInfo(String exerciseName){

        //Get data from DB corresponding with the qrcode

        correspondingExercise = db.getExerciseByname(exerciseName);

        TextView nameView = findViewById(R.id.exerciseName);
        nameView.setText(correspondingExercise.getName());

        mImageView = (ImageView) findViewById(R.id.imageView2);
        int resID = getResources().getIdentifier(correspondingExercise.getName().toLowerCase().replace(" ","").replace("é","e"), "drawable", getPackageName());
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

        // set le temps de récupération dans l'input avec la dernière récupération utilisé ou 2min de base
        if(recupList.get(0) != "") {
            TextView recupView = findViewById(R.id.editTextRecup);
            recupValue = Integer.parseInt(recupList.get(0).replace(" ",""));
            recupView.setText(transformSecondsToMinutes(recupValue));
        } else {
            TextView recupView = findViewById(R.id.editTextRecup);
            recupValue = 120;
            recupView.setText(transformSecondsToMinutes(recupValue));
        }

        for (int i = 0; i < recupList.size(); i++) {
            recupList.set(i,transformSecondsToMinutes(Integer.parseInt(recupList.get(i).replace(" ",""))));
        }

        l = (ListView) findViewById(R.id.listRecup);
        ArrayAdapter<String> arrayAdapterRecup = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                recupList );

        l.setAdapter(arrayAdapterRecup);


        List<String> suppList = new ArrayList<>();
        for (int i =0; i<poidsList.size();i++){
            suppList.add("-");
        }

        l = (ListView) findViewById(R.id.listRemove);
        ArrayAdapter<String> arrayAdapterRemove = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                suppList );
        l.setAdapter(arrayAdapterRemove);

        this.l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                deleteWorkout(position);
            }
        });


        // set le poids dans l'input avec le dernier poids utilisé ou 50kg de base
        if(poidsList.get(0) != "") {
            TextView poidsView = findViewById(R.id.poidsValue);
            poidsValue = Integer.parseInt(poidsList.get(0).replace("kg","").replace(" ",""));
            poidsView.setText(Integer.toString(poidsValue) + "kg");
        } else {
            TextView poidsView = findViewById(R.id.poidsValue);
            poidsValue = 50;
            poidsView.setText(Integer.toString(poidsValue) + "kg");
        }

    }


    public void sendNewWorkout(View v){
        if(editTextReps.getText().toString().matches("") || editTextRecup.getText().toString().matches("")){
            Toast.makeText(ExerciseInfo.this, "Au moins un champ est vide, merci de tous les remplirs", Toast.LENGTH_SHORT).show();
        } else {
            correspondingExercise.setLastsWorkoutRepetitions(correspondingExercise.getLastsWorkoutRepetitions() + ", " + editTextReps.getText().toString());
            correspondingExercise.setLastsWorkoutPoids(correspondingExercise.getLastsWorkoutPoids() + ", " + Integer.toString(poidsValue) + "kg");
            correspondingExercise.setLastsWorkoutRecup(correspondingExercise.getLastsWorkoutRecup() + ", " + transformMinutesStringToSecondes(editTextRecup.getText().toString()));
            db.updateExerciseOnWorkoutUpdate(correspondingExercise);
            getExerciseInfo(correspondingExercise.getName());
            editTextReps.setText("");

        }
    }

    public void goUserinfo(View v){
        final Intent intentUserinfo = new Intent(this, ExercisesList.class);
        startActivity(intentUserinfo);
    }




    public void ajouterPoids(View v){
        poidsValue+=2;
        TextView poidsView = findViewById(R.id.poidsValue);
        poidsView.setText(Integer.toString(poidsValue) + "kg");
    }

    public void enleverPoids(View v){
        poidsValue-=2;
        TextView poidsView = findViewById(R.id.poidsValue);
        poidsView.setText(Integer.toString(poidsValue) + "kg");
    }

    public void ajouterRecup(View v){
        recupValue+=15;
        TextView recupView = findViewById(R.id.editTextRecup);
        recupView.setText(transformSecondsToMinutes(recupValue));
    }

    public void enleverRecup(View v){
        recupValue-=15;
        TextView recupView = findViewById(R.id.editTextRecup);
        recupView.setText(transformSecondsToMinutes(recupValue));
    }

    public void deleteWorkout(int position){
        //todo faire la fonction correspondante dans sqlitemanager
        List<String> repsList = new ArrayList<>(Arrays.asList(correspondingExercise.getLastsWorkoutRepetitions().split(",")));
        Collections.reverse(repsList);
        repsList.remove(position);
        Collections.reverse(repsList);

        List<String> poidsList = new ArrayList<>(Arrays.asList(correspondingExercise.getLastsWorkoutPoids().split(",")));
        Collections.reverse(poidsList);
        poidsList.remove(position);
        Collections.reverse(poidsList);

        List<String> recupList = new ArrayList<>(Arrays.asList(correspondingExercise.getLastsWorkoutRecup().split(",")));
        Collections.reverse(recupList);
        recupList.remove(position);
        Collections.reverse(recupList);

        String repsString = "";
        for (String s : repsList) {
            repsString += s + ",";
        }
        repsString = repsString.substring(0, repsString.length() - 1);

        String poidsString = "";
        for (String s : poidsList) {
            poidsString += s + ",";
        }
        poidsString = poidsString.substring(0, poidsString.length() - 1);

        String recupString = "";
        for (String s : recupList) {
            recupString += s + ",";
        }
        recupString = recupString.substring(0, recupString.length() - 1);

        correspondingExercise.setLastsWorkoutRepetitions(repsString);
        correspondingExercise.setLastsWorkoutPoids(poidsString);
        correspondingExercise.setLastsWorkoutRecup(recupString);
        db.updateExerciseOnWorkoutUpdate(correspondingExercise);
        getExerciseInfo(correspondingExercise.getName());
    }


    public String transformSecondsToMinutes(int baseSeconds) {
        int minutes = ((baseSeconds % 86400) % 3600) / 60;
        int secondes = ((baseSeconds % 86400) % 3600) % 60;
        if (minutes == 0) {
            return Integer.toString(secondes)+"s";
        } else if (secondes == 0 ) {
            return Integer.toString(minutes)+"min";
        } else {
            return Integer.toString(minutes)+"min"+Integer.toString(secondes)+"s";
        }
    }

    public String transformMinutesStringToSecondes(String  baseMinutes) {
        int minutes;
        int secondes;

        if(baseMinutes.contains("min") && baseMinutes.contains("s")) {
            minutes = Integer.parseInt(baseMinutes.split("min")[0]) ;
            secondes = Integer.parseInt(baseMinutes.split("s")[0].split("min")[1]);
            return Integer.toString(minutes*60 + secondes);

        } else if (baseMinutes.contains("min")) {
            minutes = Integer.parseInt(baseMinutes.split("min")[0]);
            return Integer.toString(minutes*60);

        } else {
            return baseMinutes.split("s")[0];
        }
    }
}
