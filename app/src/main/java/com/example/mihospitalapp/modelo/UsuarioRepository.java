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

    public void cambiarEstadoFirebase(String codigo, String estado,GestorBD gestor){
        gestor.getPersonalReference().orderByChild("codigo").equalTo(codigo)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Recorrer los nodos encontrados
                            for (DataSnapshot child : snapshot.getChildren()) {
                                // Actualizar el campo "estado" del nodo correspondiente
                                child.getRef().child("estado").setValue(estado)
                                        .addOnSuccessListener(aVoid -> System.out.println("Estado cambiado correctamente para el código: " + codigo))
                                        .addOnFailureListener(e -> System.out.println("Error al cambiar el estado: " + e.getMessage()));
                            }
                        } else {
                            System.out.println("El código especificado no existe en la tabla personal.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        System.out.println("Error en la consulta: " + error.getMessage());
                    }
                });
    }
    public String nombreCompletoUsuario(GestorBD gestor) {
        String fullName = null;
        Cursor cursor = gestor.getMySQLite().getWritableDatabase().query(gestor.getMySQLite().TABLE_NAME,
                new String[]{gestor.getMySQLite().NOMBRE, gestor.getMySQLite().APELLIDO},
                null,
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(MySQLite.NOMBRE));
                @SuppressLint("Range") String apellido = cursor.getString(cursor.getColumnIndex(MySQLite.APELLIDO));
                fullName = nombre + " " + apellido;
            }
            cursor.close();
        }
        return fullName;
    }

}
