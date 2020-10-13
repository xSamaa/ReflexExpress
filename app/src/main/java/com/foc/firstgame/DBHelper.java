package com.foc.firstgame;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME = "BDUsuarios"; //Nombre de la BD
    static int DATABASE_VERSION = 1; // Versión de la BD
    static String TABLE_NAME = "Usuarios";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //Constructor que llama a super con los datos necesarios


    //Se llama cuando se instancia DBHelper
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios(nombre TEXT PRIMARY KEY, puntuacion INT )");exe
    }
    //Se llama cuando la DATABASE_VERSION es mayor
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE " + TABLE_NAME );
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "( id INT PRIMARY KEY, " +
                "nombre VARCHAR(20));");
    }
    //Método para consultar la tabla con el método query
    public Cursor getUsuarios(){
        //Pedir BD con permisos de lectura
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME,null,null,null,null,null,null);
    }
    //Método para consultar la tabla con un SELECT
    public Cursor getUsuariosRaw(){
        //Pedir BD con permisos de lectura
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }
    //Método para insertar un usuario
    public long insertarUsuario(String nombre){
        //Pedimos la bd con permisos de Escritura
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues(); //ContenValues guarda duplas (clave , valor) -> clave es la columna de la tabla y valor el valor de la columna
        value.put("nombre",nombre);
        //Inserción parametrizada, requiere los valores con contentValues
        return db.insert(TABLE_NAME,null,value);
    }
}