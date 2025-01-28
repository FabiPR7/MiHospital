package com.example.mihospitalapp.fragments;

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
import com.example.mihospitalapp.activities.PersonalActivty;
import com.example.mihospitalapp.modelo.GestorBD;
import com.example.mihospitalapp.modelo.Personal;
import com.example.mihospitalapp.modelo.PersonalRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class registrateCompleto2 extends Fragment {

    private ImageView iniciarsesion;
    private Button aceptar;
    private EditText nombre,apellido,correo,contrasena,confirmacontra,codigo;
    private ArrayList<String> hospitalCodes;
    private ArrayList<String> personalCodes;
    private GestorBD migestor;
    public registrateCompleto2() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrate_completo2, container, false);
        iniciarsesion = view.findViewById(R.id.iniciarSesionButon2);
        nombre = view.findViewById(R.id.nombreReistro2);
        apellido = view.findViewById(R.id.apellidoRegistro2);
        correo = view.findViewById(R.id.correoRegistro2);
        contrasena = view.findViewById(R.id.contrasenaRegistro2);
        confirmacontra= view.findViewById(R.id.cofnirmacontrRegistro2);
        codigo = view.findViewById(R.id.codigoRegistro2);
        hospitalCodes = new ArrayList<>();
        personalCodes = new ArrayList<>();
        migestor = new GestorBD(getContext());
        aceptar = view.findViewById(R.id.aceptarButton2);
        fetchHospitalCodes(success -> {
            if (success) {
                System.out.println("All Hospitales  Hecho");
                System.out.println(hospitalCodes.toString());
            } else {
                System.out.println("Error o datos no encontrados.");
            }
        });
        fetchPersonalCodes(success -> {
            if (success) {
                System.out.println("All personal  Hecho");
                System.out.println(personalCodes.toString());
            } else {
                System.out.println("Error o datos no encontrados.");
            }
        });
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aceptar();
            }
        });
        iniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cambiarFragment(view);
            }
        });
        return view;
    }
    public interface OnCompleteListener {
        void onComplete(boolean success);
    }

    private void fetchHospitalCodes(OnCompleteListener listener) {
     migestor.getHospitalReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hospitalCodes.clear(); // Limpiar lista para evitar duplicados
                for (DataSnapshot hospitalSnapshot : snapshot.getChildren()) {
                    String codigo = hospitalSnapshot.child("codigo").getValue(String.class);
                    if (codigo != null) {
                        hospitalCodes.add(codigo);
                        System.out.println(codigo);
                    }
                }
                listener.onComplete(true);
                Log .d("HospitalCodes", "Códigos: " + hospitalCodes);
                Toast.makeText(getContext(), "CODIGOS ENCONTRADOS", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "NO HAY DATOS", Toast.LENGTH_SHORT).show();
                Log.e("FirebaseError", "Error al obtener los datos: " + error.getMessage());
            listener.onComplete(false);
            }
        });
    }

    private void fetchPersonalCodes(OnCompleteListener listener) {
        migestor.getPersonalReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                personalCodes.clear(); // Limpiar lista para evitar duplicados
                for (DataSnapshot hospitalSnapshot : snapshot.getChildren()) {
                    String codigo = hospitalSnapshot.child("codigo").getValue(String.class);
                    if (codigo != null) {
                        personalCodes.add(codigo);
                    }
                }
                listener.onComplete(true);
                System.out.println("HospitalCodes Códigos: " + hospitalCodes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error al obtener los datos: " + error.getMessage());
                listener.onComplete(false);
            }
        });
    }

    public void cambiarFragment(View view){
        ImageView imageView = view.findViewById(R.id.iniciarSesionButon2);
        imageView.setOnClickListener(v -> {
            registrarCompleto registro1 = new registrarCompleto();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, registro1)
                    .addToBackStack(null)
                    .commit();
        });
    }
    public void aceptar(){
        if (verificarInformacion()){
            insertar();
            Toast.makeText(this.getContext(), "Se agrego correctamente, ahora accede con tus datos", Toast.LENGTH_SHORT).show();
        }
        else{
         System.out.println("ERROR EN ALGO");
        }
    }

    public Boolean verificarInformacion(){
        boolean vacio = nombre.getText().toString().isEmpty() || apellido.getText().toString().isEmpty()
                || correo.getText().toString().isEmpty() || contrasena.getText().toString().isEmpty()
                || confirmacontra.getText().toString().isEmpty() || codigo.getText().toString().isEmpty();
        if (vacio){
            Toast.makeText(this.getContext(), "Algun dato esta vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean nombreApellido = nombre.getText().toString().matches((".*\\d.*")) || apellido.getText().toString().matches((".*\\d.*"));
        if(nombreApellido){
            Toast.makeText(this.getContext(), "Nombre o Apellido contiene numeros", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean contra = contrasena.getText().toString().length() < 8;
        if (contra)
        {
            Toast.makeText(this.getContext(), "La contraseña son minimo 8 carácteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean confirmacontrasena = contrasena.getText().toString().equals(confirmacontra.getText().toString());
        if(!confirmacontrasena){
            Toast.makeText(this.getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean correoC = correo.getText().toString().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        if(!correoC){
            Toast.makeText(this.getContext(), "Formato de correo incorrecto", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean formatoCodigo = Pattern.matches("^.+-.+$", codigo.getText().toString());
        if (!formatoCodigo){
            Toast.makeText(this.getContext(), "Formato de codigo incorrecto", Toast.LENGTH_SHORT).show();
            return false;
        }
        String[] codes = codigo.getText().toString().split("-");
       boolean codigoPersonalFomrato = Pattern.matches("^\\d{4}$", codes[1]);
        if (!codigoPersonalFomrato){
            Toast.makeText(this.getContext(), "Formato de codigo personal", Toast.LENGTH_SHORT).show();
            return false;
        }
       boolean codigoPersonal = personalCodes.contains(codigo.getText().toString());
        if (codigoPersonal){
            Toast.makeText(this.getContext(), "Este codigo ya esta en uso", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean codigoHospitalFormato = Pattern.matches("^H\\d{3}$",codes[0]);
        if (!codigoHospitalFormato){
            Toast.makeText(this.getContext(), "Formato de codigo de hospital incorrecto", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean codigoHospital = false;
        if (!codigoHospital){
            Toast.makeText(this.getContext(), "Tu codigo "+codes[0] +" (Los esperados son)"+hospitalCodes.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    public void insertar(){
        String n,a,c,cn1,cod;
        n = nombre.getText().toString();
        a = apellido.getText().toString();
        c = correo.getText().toString();
        cn1 = contrasena.getText().toString();
        cod = codigo.getText().toString();
        Personal p = new Personal(n,a,c,cn1,cod,"activo");
        GestorBD gestorBD = new GestorBD(getContext());
        gestorBD.insertarPersonal(p);
    }
}