package com.example.cycledevieuneapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editTextPrenom;
    private EditText editTextPassword;
    private Button buttonSendButton;
    private String prenom;
    private String password;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("LyfeCycle", "onCreate");
        editTextPrenom = (EditText) findViewById(R.id.EditTextPrenom);
        editTextPassword = (EditText) findViewById(R.id.EditTextPassword);
        buttonSendButton = (Button) findViewById((R.id.SendButton));

    }

    public void envoyer(View v){
        prenom = editTextPrenom.getText().toString();
        password = editTextPassword.getText().toString();
        //if(checkUser(prenom, password))
        //    Toast.makeText(MainActivity.this, "Identifiants valid, bienvenue " + prenom + " !", Toast.LENGTH_SHORT).show();
        //else
        //   Toast.makeText(MainActivity.this, "Identifiants inccorect, try again " + prenom + " !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, UserActivity.class);

        if(checkUser(prenom, password)){
            intent.putExtra(EXTRA_MESSAGE, prenom);
            startActivity(intent);
        }
        else
           Toast.makeText(MainActivity.this, "Incorrect user or login, try again " + prenom + " !", Toast.LENGTH_SHORT).show();


        // ((TextView) findViewById(R.id.TextViewHello)).setText("hello"+prenom);
    }



    public boolean checkUser(String u_prenom, String u_password){
        String realPrenom = "Florian";
        String realPassword = "123";

        if(u_prenom.equals(realPrenom) && u_password.equals(realPassword)){
            return true;
        }else{
            return false;
        }
    }


    public void goUserinfo(View v){
        final Intent intentUserinfo = new Intent(this, userinfo.class);
        startActivity(intentUserinfo);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LyfeCycle", "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LyfeCycle", "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LyfeCycle", "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LyfeCycle", "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LyfeCycle", "onDestroy");
    }
}
