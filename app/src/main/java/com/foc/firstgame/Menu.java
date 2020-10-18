package com.foc.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    private EditText txtNombre;
    private ListView lvLista;
    private Switch sTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        lvLista = (ListView) findViewById(R.id.lvLista);
        sTime = findViewById(R.id.sTime);
    }

    public void verClasi(View view) {
        // Abrir la base de datos.
        DBHelper db = new DBHelper(this,"clasificacion", null, 1);
        SQLiteDatabase baseDeDatos = db.getWritableDatabase();

        Cursor fila = baseDeDatos.rawQuery("select * from usuarios order by puntuacion desc", null);

        ArrayList<String> datos = new ArrayList<String>();

        if(fila.moveToNext()){
            do{
                datos.add("# " +fila.getString(1)+"     "+ fila.getString(0) + "\n");
            }while(fila.moveToNext());
        }
        //VISUALIZACION, por defenco estan en Android.Layout
        // Adaptador
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);

        lvLista.setAdapter(adaptador);

    }

    public void verJugar(View view) {

        boolean turbo=false;
        if(sTime.isChecked()){
            turbo= true;
        }

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("nombre", txtNombre.getText().toString());
        intent.putExtra("turbo", turbo);
        startActivity(intent);

    }
}