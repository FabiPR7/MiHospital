package com.example.mihospitalapp.run;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.mihospitalapp.R;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mihospitalapp.activities.MenuPrincipal;
import com.example.mihospitalapp.modelo.GestorBD;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private GestorBD miGestor;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        miGestor = new GestorBD(this);
       estaRegistrado();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void estaRegistrado(){
        if (miGestor.isUserRegistered(1)){
            Intent i = new Intent(MainActivity.this, MenuPrincipal.class);
            startActivity(i);
        }



    }
}