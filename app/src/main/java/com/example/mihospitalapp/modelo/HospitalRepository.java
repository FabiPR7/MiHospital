package com.example.mihospitalapp.modelo;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalRepository {

    private  String nombreHospital;

    public String obtenerNombreHospital(String codigoBuscado, UsuarioRepository.OnCompleteListener listener) {
        DatabaseReference hospitalesRef = FirebaseDatabase.getInstance().getReference("hospitales");
        hospitalesRef.orderByChild("codigo").equalTo(codigoBuscado).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot hospitalSnapshot : snapshot.getChildren()) {
                        nombreHospital = hospitalSnapshot.child("nombre").getValue(String.class);
                        if (nombreHospital != null) {
                            listener.onComplete(true); // Devuelve el nombre del hospit
                        }
                    }
                }
                else{
                    listener.onComplete(false); // Código no encontrado
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error al obtener el hospital: " + error.getMessage());
                listener.onComplete(false); // Maneja errores
            }
        });
        if (nombreHospital!=null){
            return nombreHospital;
        }
        else {
            return "";
        }
    }

    public String obtenerCodigoHospital(String codigo){
        String[] codigoSplit = codigo.split("-");
        return codigoSplit[0];
      }

    public String getNombreHospital() {
        return nombreHospital;
    }
    public String nombreHospitalMysqlite(GestorBD gestor) {
        String nombre = null;
        Cursor cursor = gestor.getDatabase().query("hospital",
                new String[]{"nombre"},
                null,
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String nombreH = cursor.getString(cursor.getColumnIndex("nombre"));
                nombre = nombreH;
            }
            cursor.close();
        }
        return nombre;
    }
    public String codigoHospitalMysqlite(GestorBD gestor) {
        String codigo = null;
        Cursor cursor = gestor.getDatabase().query("hospital",
                new String[]{"codigo"},
                null,
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String codigoH = cursor.getString(cursor.getColumnIndex("nombre"));
                codigo = codigoH;
            }
            cursor.close();
        }
        return codigo;
    }
}
