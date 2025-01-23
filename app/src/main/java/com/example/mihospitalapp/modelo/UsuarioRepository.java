package com.example.mihospitalapp.modelo;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UsuarioRepository {

    @SuppressLint("Range")
    public String obtenerCodigo(GestorBD gestor){
        SQLiteDatabase db = gestor.getMySQLite().getReadableDatabase(); // Obtén la base de datos en modo lectura
        Cursor cursor = null;
        String codigo = "";
        try {
            cursor = db.query(
                    MySQLite.TABLE_NAME,                  // Nombre de la tabla
                    new String[]{"codigo"},               // Columnas a seleccionar
                    null,                                 // No hay cláusula WHERE
                    null,                                 // No hay argumentos para WHERE
                    null,                                 // No hay cláusula GROUP BY
                    null,                                 // No hay cláusula HAVING
                    null                                  // No hay cláusula ORDER BY
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                    System.out.println("Código: " + codigo);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close(); // Asegúrate de cerrar el cursor
            }
        }
        return  codigo;
    }

    public interface OnCompleteListener {
        void onComplete(boolean result);
    }

    private  String nombreHospital;

    public String getNombreHospital() {
        return nombreHospital;
    }

    public String obtenerNombreHospital(String codigoBuscado, OnCompleteListener listener) {
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
                listener.onComplete(false); // Código no encontrado
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

}
