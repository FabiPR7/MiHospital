package com.example.mihospitalapp.modelo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GestorBD {

    private DatabaseReference mDatabase;
    private SQLiteDatabase database;
    private MySQLite mySQLite;

    public GestorBD(Context context){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mySQLite = new MySQLite(context);
        database = mySQLite.getWritableDatabase();
    }
    public GestorBD(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        database = mySQLite.getWritableDatabase();
    }

    public void insertarPersonal(Personal personal){
        mDatabase.child("personal").push().setValue(personal)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Operación exitosa
                        Log.d("Firebase", "Datos insertados correctamente");
                    } else {
                        // Operación fallida
                        Log.e("Firebase", "Error al insertar datos", task.getException());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error al insertar datos en Firebase", e);
                });
    }
    public void closeSQLite() {
        mySQLite.close();
    }

    // Insertar el usuario
    public long insertUsuario(Usuario user) {
        ContentValues values = new ContentValues();
        values.put(mySQLite.ID, user.getCodigo());
        values.put(mySQLite.NOMBRE, user.getNombre());
        values.put(mySQLite.APELLIDO, user.getApellido());
        values.put(mySQLite.ESTADO_ACTIVO, user.isEstaActivo() ? 1 : 0);
        values.put(mySQLite.ESTADO_REGISTRADO, user.isEstaRegistrado() ? 1 : 0);
        return database.insert(mySQLite.TABLE_NAME, null, values);
    }

    public boolean cambiarEstadoUsuario(int id, boolean estaActivo, boolean estaRegistrado) {
        ContentValues values = new ContentValues();
        values.put(mySQLite.ESTADO_ACTIVO, estaActivo ? 1 : 0);
        values.put(mySQLite.ESTADO_REGISTRADO, estaRegistrado ? 1 : 0);
        int filasAfectadas = database.update(mySQLite.TABLE_NAME, values,
                mySQLite.ID + " = ?", new String[]{String.valueOf(id)});
        return filasAfectadas > 0;
    }

    public boolean isTableNotEmpty() {
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT COUNT(*) FROM " + mySQLite.TABLE_NAME, null);
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0); // Obtiene el valor del primer campo (conteo de filas)
                return count > 0;
            }
        } finally {
            if (cursor != null) {
                cursor.close(); // Asegúrate de cerrar el cursor
            }
        }
        return false; // Retorna false si no hay registros o ocurre algún error
    }



    public boolean isUserActive(int userId) {
        Cursor cursor = database.query(MySQLite.TABLE_NAME,
                new String[]{MySQLite.ESTADO_ACTIVO},
                MySQLite.ID + " = ?",
                new String[]{String.valueOf(userId)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int activo = cursor.getInt(cursor.getColumnIndex(MySQLite.ESTADO_ACTIVO));
                cursor.close();
                return activo == 1;
            }
            cursor.close();
        }
        return false;
    }

    public String nombreCompletoUsuario() {
        String fullName = null;
        Cursor cursor = database.query(mySQLite.TABLE_NAME,
                new String[]{mySQLite.NOMBRE, mySQLite.APELLIDO},
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


    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public MySQLite getMySQLite() {
        return mySQLite;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
