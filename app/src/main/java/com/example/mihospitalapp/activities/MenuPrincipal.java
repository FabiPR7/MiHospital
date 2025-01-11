package com.example.mihospitalapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mihospitalapp.R;
import com.example.mihospitalapp.modelo.GestorBD;
import com.example.mihospitalapp.modelo.PersonalRepository;

public class MenuPrincipal extends AppCompatActivity {

    private TextView nombre, hospital;
    private Switch aSwitchMenu;
    private ImageView personalB, pacientesB,tareasB,habitacionesB,ajustesB,perfilB;
    private PersonalRepository pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal);
        pr = new PersonalRepository();
        pr.procesoAllPersonal(new GestorBD(this));
        nombre = findViewById(R.id.nombreMenu);
        hospital = findViewById(R.id.hopsitalMenu);
        aSwitchMenu = findViewById(R.id.switchMenu);
        personalB = findViewById(R.id.personalButton);
        pacientesB = findViewById(R.id.pacientesButton);
        tareasB = findViewById(R.id.tareasButton);
        habitacionesB = findViewById(R.id.habitacionesButton);
        ajustesB = findViewById(R.id.ajustesButton);
        perfilB = findViewById(R.id.perfilButton);
        colocarNombres();
        GestorBD gestorBD = new GestorBD(this);
        personalB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPersonal();
            }
        });
        if (gestorBD.isUserActive(1)){
            aSwitchMenu.setChecked(true);
        }
        verificarSwitch();
        aSwitchMenu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                verificarSwitch();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void activityPersonal(){
        Intent i = new Intent(MenuPrincipal.this, PersonalActivty.class);
        startActivity(i);
    }

    public void colocarNombres(){
        GestorBD gestor = new GestorBD(this);
        String nombreCompleto = gestor.nombreCompletoUsuario();
        nombre.setText(nombreCompleto);
        hospital.setText("San Pedro");
    }

    public void verificarSwitch(){
        habilitarOdeshabilitar(perfilB);
        habilitarOdeshabilitar(tareasB);
        habilitarOdeshabilitar(pacientesB);
        habilitarOdeshabilitar(personalB);
        habilitarOdeshabilitar(ajustesB);
        habilitarOdeshabilitar(habitacionesB);
    }

    public void habilitarOdeshabilitar(View view){
        GestorBD gestorBD = new GestorBD(this);
      if (!aSwitchMenu.isChecked()){
        view.setVisibility(View.INVISIBLE);
        view.setEnabled(false);
        gestorBD.cambiarEstadoUsuario(1,false,true);
      }
      else {
          view.setVisibility(View.VISIBLE);
          view.setEnabled(true);
          gestorBD.cambiarEstadoUsuario(1,true,true);
      }
    }

}