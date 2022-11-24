package com.example.cycledevieuneapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class userinfo extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.muscuApp.MESSAGE";
    private ListView l;
    private ArrayList exercisesList = new ArrayList();
    private ArrayAdapter<Exercises> listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        Log.d("aaa", Calendar.getInstance().getTime().toString());

        User user = new User("Mark", "24");

        TextView nameView = findViewById(R.id.userName);
        nameView.setText(user.getName());
        //Todo pitet mettre date du dernier entrainement
        TextView ageView = findViewById(R.id.userAge);
        ageView.setText(user.getAge());


        getUsedExercisesList();

    }

    public void getUsedExercisesList(){
        //pitet remetre en sorte que si on click sur un exo, on arrive sr ExoInfo comme si on l'avais scan

        SQLiteManager db = new SQLiteManager(this);
        // Créer les exercises de base dans la base de donné seulement si elle est vide
        if(db.getExercisesNumber() == 0){
            db.createDefaultExo();
        }

        this.l = (ListView) findViewById(R.id.listExercises);
        List<Exercises> list =  db.getAllExercisesNamesSortByDate();
        Collections.reverse(list);
        this.exercisesList.addAll(list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                exercisesList );

        this.l.setAdapter(arrayAdapter);

        this.l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                runActivityExoInfo(l.getItemAtPosition(position).toString());
            }
        });

    }



    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
       // sqLiteManager.populateExercisesListArray();
    }

    public void createExercises() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);

        Exercises newExo = new Exercises("1","Dips","Exercise poly-articulaire travaillant principalement les pecs et les triceps.",
                "pecs, triceps", "8 8 8 7, 9 8 7 7", "12-12-2022");

        Exercises.exoArrayList.add(newExo);
        sqLiteManager.addExerciseToDatabase(newExo);

        finish();
    }








    // QrCode Scan
    public void scanButtonPressed(View v){
        scanCode();
    }

    public void scanCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning code");
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){
                runActivityExoInfo(result.getContents());
            }
            else {
                Toast.makeText(this, "Aucun resultat", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void runActivityExoInfo(String exerciseName){
        Intent intent = new Intent(this, ExerciseInfo.class);
        intent.putExtra(EXTRA_MESSAGE, exerciseName);
        startActivity(intent);
    }

}
