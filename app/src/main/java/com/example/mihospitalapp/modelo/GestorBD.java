package com.example.mihospitalapp.modelo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GestorBD {

    private DatabaseReference mDatabase;
    private SQLiteDatabase sqlLiteWritable;
    private MySQLite mySQLite;


    public GestorBD(Context context){
        FirebaseApp.initializeApp(context);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mySQLite = new MySQLite(context);
        sqlLiteWritable = mySQLite.getWritableDatabase();
    }
    public GestorBD(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sqlLiteWritable = mySQLite.getWritableDatabase();
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
    public long insertarUsuario(Usuario user) {
        ContentValues values = new ContentValues();
        values.put(mySQLite.ID, user.getCodigo());
        values.put(mySQLite.NOMBRE, user.getNombre());
        values.put(mySQLite.APELLIDO, user.getApellido());
        values.put(mySQLite.ESTADO_ACTIVO, user.isEstaActivo() ? 1 : 0);
        values.put(mySQLite.ESTADO_REGISTRADO, user.isEstaRegistrado() ? 1 : 0);
        return sqlLiteWritable.insert(mySQLite.TABLE_NAME, null, values);
    }

    // Insertar el hospital
    public long insertarHospital(Hospital hospital) {
        ContentValues values = new ContentValues();
        values.put("codigo", hospital.getCodigo());
        values.put("nombre", hospital.getNombre());
        return sqlLiteWritable.insert("hospital", null, values);
    }



    public boolean verificarTabla_vacia() {
        Cursor cursor = null;
        try {
            cursor = sqlLiteWritable.rawQuery("SELECT COUNT(*) FROM " + mySQLite.TABLE_NAME, null);
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


    public DatabaseReference getPersonalReference(){
        return FirebaseDatabase.getInstance().getReference("personal");
    }
    public DatabaseReference getHospitalReference(){
        return FirebaseDatabase.getInstance().getReference("hospitales");
    }
    public DatabaseReference getMensajeReference(){
        return FirebaseDatabase.getInstance().getReference("mensajes");
    }
    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public MySQLite getMySQLite() {
        return mySQLite;
    }

    public SQLiteDatabase getDatabase() {
        return sqlLiteWritable;
    }
}
