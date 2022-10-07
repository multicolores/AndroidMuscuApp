package com.example.cycledevieuneapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;


public class UserActivity extends AppCompatActivity {

    ListView l;
    String items[] = { "Voir les stations Vélib", "A propos des Vélib" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.userName);
        textView.setText("Bienvenue "+message);


        l = findViewById(R.id.listMenu);
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        l.setAdapter(arr);

        //ListAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] { "name" }, new int[] { android.R.id.text1 });

        //findViewById(R.support_simple_spinner_dropdown_item);

        final Intent intentVelib = new Intent(this, VelibAbout.class);


        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                //textView.setText("The best football player is : " + selectedItem);
                Log.d("clicked", selectedItem);   // output :   clicked: A propos des Vélib
                switch(selectedItem) {
                    case "Voir les stations Vélib":
                        Toast.makeText(UserActivity.this, "En cours de développement", Toast.LENGTH_SHORT).show();
                        break;
                    case "A propos des Vélib":
                        startActivity(intentVelib);
                        break;
                    default:
                        Log.d("clicked", selectedItem);
                }
            }
        });

    }




}
