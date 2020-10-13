package com.foc.firstgame;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnIzq, btnDer, btnCambio;
    private boolean aleatorio;
    private int puntuacion;
    private DBHelper db =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIzq = findViewById(R.id.btnIzq);
        btnDer = findViewById(R.id.btnDer);
        btnCambio = findViewById(R.id.btnCambio);


        colorAutomatico();


    }

    public void clickIzquierdo(View view) {
        if(btnCambio.getText().equals(btnIzq.getText())){
            puntuacion++;
            colorAutomatico();
        }else{
            finaliza();
        }
    }

    public void clickDerecho(View view) {
        if(btnCambio.getText().equals(btnDer.getText())){
            puntuacion++;
            colorAutomatico();
        }else{
            finaliza();

        }
    }

    private void finaliza() {
        db=new DBHelper(this,"clasificacion", null,1);
        SQLiteDatabase baseDeDatos = db.getWritableDatabase();

        String nombre = getIntent().getStringExtra("nombre");

        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("puntuacion",puntuacion);

        baseDeDatos.insert("usuarios",null, registro);
        baseDeDatos.close();


        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), getIntent().getStringExtra("nombre") +", tu puntuación es " + puntuacion, Toast.LENGTH_SHORT).show();

    }

    public void colorAutomatico() {
        aleatorio = Math.random()<0.5;

        if (aleatorio){
            btnCambio.setBackgroundColor(Color.BLACK);
            btnCambio.setText("negro");
            btnCambio.setTextColor(Color.BLACK);


        }else{
            btnCambio.setBackgroundColor(Color.RED);
            btnCambio.setText("rojo");
            btnCambio.setTextColor(Color.RED);

        }
    }

}