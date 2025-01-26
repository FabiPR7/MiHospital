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


    private static final String CREATE_TABLE_USUARIO =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    ID + " TEXT PRIMARY KEY NOT NULL, " +
                    NOMBRE + " TEXT, " +
                    APELLIDO + " TEXT, " +
                    ESTADO_ACTIVO + " INTEGER, " + // 0 = false, 1 = true
                    ESTADO_REGISTRADO + " INTEGER)"; // 0 = false, 1 = true

    private final String CREATE_TABLE_HOSPITAL =
            "CREATE TABLE IF NOT EXISTS hospital (" +
                    "codigo TEXT PRIMARY KEY NOT NULL, " +
                    "nombre TEXT) ";

    private final String CREATE_TABLE_MENSAJE =
            "CREATE TABLE IF NOT EXISTS mensaje (" +
                    "codigo TEXT PRIMARY KEY NOT NULL, " +
                    "contenido TEXT," +
                    "autor TEXT, " +
                    "receptor TEXT," +
                    "fecha_hora" +
                    ") ";

    public MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_HOSPITAL);
        db.execSQL(CREATE_TABLE_MENSAJE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS  mensaje");
        db.execSQL("DROP TABLE IF EXISTS hospital" );
        onCreate(db);
    }
    public void borrarTodo(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        db.execSQL("DROP TABLE hospital");
        db.execSQL("DROP TABLE hospital");
    }
    public void crarTodo(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_HOSPITAL);
        db.execSQL(CREATE_TABLE_MENSAJE);
    }


}
