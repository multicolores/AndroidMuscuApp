package com.example.cycledevieuneapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExercisesList extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.muscuApp.MESSAGE";
    private ListView l;
    private ArrayList exercisesList = new ArrayList();
    private ArrayAdapter<Exercises> listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        //Log.d("aaa", Calendar.getInstance().getTime().toString());

        getUsedExercisesList();

    }

    public void getUsedExercisesList(){
        SQLiteManager db = new SQLiteManager(this);

        // Créer les exercises dans la base de donnée si elle est vide
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
