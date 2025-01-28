package com.example.mihospitalapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mihospitalapp.R;
import com.example.mihospitalapp.activities.MenuPrincipal;
import com.example.mihospitalapp.modelo.GestorBD;
import com.example.mihospitalapp.modelo.Hospital;
import com.example.mihospitalapp.modelo.HospitalRepository;
import com.example.mihospitalapp.modelo.Usuario;
import com.example.mihospitalapp.run.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class registrarCompleto extends Fragment {

    private ImageView registrate;
    private Button aceptar;
    private EditText correo, contrasena;
    private GestorBD miGestor;
    private HospitalRepository hr;
    public  boolean nombre;
    public registrarCompleto() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrar_completo, container, false);

        registrate = view.findViewById(R.id.registrateButon1);
        correo = view.findViewById(R.id.correoRegistro1);
        contrasena = view.findViewById(R.id.contrasenaRegistro1);
        aceptar = view.findViewById(R.id.aceptarButton1);
        hr = new HospitalRepository();
        miGestor = new GestorBD(getContext());
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aceptar();
            }
        });
        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarFragment(view);
            }
        });
        return view;
    }

    public void cambiarFragment(View view){
        ImageView imageView = view.findViewById(R.id.registrateButon1);
        imageView.setOnClickListener(v -> {
            registrateCompleto2 registro2 = new registrateCompleto2();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, registro2) // Cambia al nuevo fragmento
                    .addToBackStack(null) // Agrega a la pila para permitir retroceder
                    .commit();
        });
    }

    public void aceptar(){
        verificarInformacion();
    }

    public void verificarInformacion(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("personal");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean loginExitoso = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String correoDb = snapshot.child("correo").getValue(String.class);
                    String contrasenaDb = snapshot.child("contraseña").getValue(String.class);
                    if (correo.getText().toString().equals(correoDb) && contrasena.getText().toString()                         .equals(contrasenaDb)) {
                        loginExitoso = true;
                        Usuario user = new Usuario(snapshot.child("codigo").getValue(String.class),snapshot.child("nombre").getValue(String.class),snapshot.child("apellido").getValue(String.class),true,true);
                        String codigoHospital = hr.obtenerCodigoHospital(user.getCodigo());
                        hr.obtenerNombreHospital(hr.obtenerCodigoHospital(user.getCodigo()),
                                sucess -> {
                                    if(sucess){
                                        Hospital hospital = new Hospital(codigoHospital,hr.getNombreHospital());
                                        miGestor.insertarHospital(hospital);
                                    }
                                    else{
                                        System.out.println("Error al obtener nombre Hospital");
                                    }
                                });
                        miGestor.insertarUsuario(user);
                        Intent i = new Intent(getContext(), MenuPrincipal.class);
                        startActivity(i);
                        break;
                    }
                }
                if (loginExitoso) {
                    Toast.makeText(getContext(), "SI FUNCIONO", Toast.LENGTH_SHORT).show();
                    Log.d("Firebase", "Inicio de sesión exitoso");
                    // Redirigir al usuario o mostrar un mensaje
                } else {
                    Toast.makeText(getContext(), "NO FUNCIONO", Toast.LENGTH_SHORT).show();
                    Log.e("Firebase", "Correo o contraseña incorrectos");
                    // Mostrar un mensaje de error al usuario
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error en la consulta: " + error.getMessage());
            }
        });
    }
}