package com.foc.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    private EditText txtNombre;
    private TextView tv11,tv12,tv21,tv22,tv31,tv32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        tv11 = (TextView)findViewById(R.id.tv11);
        tv12 = (TextView)findViewById(R.id.tv12);
        tv21 = (TextView)findViewById(R.id.tv21);
        tv22 = (TextView)findViewById(R.id.tv22);
        tv31 = (TextView)findViewById(R.id.tv31);
        tv32 = (TextView)findViewById(R.id.tv32);
    }

    public void verClasi(View view) {

        DBHelper db = new DBHelper(this,"clasificacion", null, 1);
        SQLiteDatabase baseDeDatos = db.getWritableDatabase();

        Cursor fila = baseDeDatos.rawQuery("select * from usuarios order by puntuacion asc", null);

        if(fila.moveToFirst()){
            do{
                tv11.setText(fila.getString(0));
                tv12.setText(fila.getString(1));
                tv11.setVisibility(View.VISIBLE);
                tv12.setVisibility(View.VISIBLE);
            }while(fila.moveToNext());
            baseDeDatos.close();
        }else {
            Toast.makeText(this,"No encuentro",Toast.LENGTH_SHORT).show();
            baseDeDatos.close();
        }

    }

    public void verJugar(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("nombre", txtNombre.getText().toString());
        startActivity(intent);

    }
}