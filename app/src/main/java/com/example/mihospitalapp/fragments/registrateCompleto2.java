package com.example.mihospitalapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mihospitalapp.R;
import com.example.mihospitalapp.modelo.GestorBD;
import com.example.mihospitalapp.modelo.Personal;


public class registrateCompleto2 extends Fragment {
    private ImageView iniciarsesion;
    private Button aceptar;
    private EditText nombre,apellido,correo,contrasena,confirmacontra,codigo;

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
        aceptar = view.findViewById(R.id.aceptarButton2);
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
            Toast.makeText(this.getContext(), "Se agrego correctamente", Toast.LENGTH_SHORT).show();
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
        return true;
    }

    public void insertar(){
        String n,a,c,cn1,cod;
        n = nombre.getText().toString();
        a = apellido.getText().toString();
        c = correo.getText().toString();
        cn1 = contrasena.getText().toString();
        cod = codigo.getText().toString();
        Personal p = new Personal(n,a,c,cn1,cod);
        GestorBD gestorBD = new GestorBD(getContext());
        gestorBD.insertarPersonal(p);
    }
}