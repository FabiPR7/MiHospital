package com.example.mihospitalapp.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "usuario.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "usuario";
    public static final String ID = "codigo";
    public static final String NOMBRE = "nombre";
    public static final String APELLIDO = "apellido";
    public static final String ESTADO_ACTIVO = "esta_activo";
    public static final String ESTADO_REGISTRADO = "esta_registrado";


    private static final String CREATE_TABLE =
                    "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " TEXT PRIMARY KEY NOT NULL, " +
                    NOMBRE + " TEXT, " +
                    APELLIDO + " TEXT, " +
                    ESTADO_ACTIVO + " INTEGER, " + // 0 = false, 1 = true
                    ESTADO_REGISTRADO + " INTEGER)"; // 0 = false, 1 = true

    public MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void borrarTodo(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
    }
    public void crarTodo(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

}
