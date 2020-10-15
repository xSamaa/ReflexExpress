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
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnIzq, btnDer, btnCambio;
    private boolean aleatorio;
    private int puntuacion;
    private DBHelper db =null;
    private Animation animacion;

    private TextView tvTemporizador,tvPuntuacion,tvTiempo;
    private CountDownTimer countDownTimer,countDownTimer2;
    private long restanteMilisegundos = 10000; // 10 Segundos.
    private long tresSegundos = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIzq = findViewById(R.id.btnIzq);
        btnDer = findViewById(R.id.btnDer);
        btnCambio = findViewById(R.id.btnCambio);

        animacion = AnimationUtils.loadAnimation(this, R.anim.scale);

        tvPuntuacion = findViewById(R.id.tvPuntuacion);

        tvTemporizador = findViewById(R.id.tvTemporizador);
        tvTiempo = findViewById(R.id.tvTiempo);

        cuentaAtras();
    }
    private void empiezar(){
        timer();
        colorAutomatico();
    }

    private void cuentaAtras(){
        btnCambio.setVisibility(View.INVISIBLE);
        countDownTimer2 = new CountDownTimer(tresSegundos,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTiempo.setText(String.valueOf(millisUntilFinished/1000+1));
            }

            @Override
            public void onFinish() {
                tvTiempo.setVisibility(View.INVISIBLE);
                empiezar();
            }
        }.start();
    }

    private void timer() {
        countDownTimer = new CountDownTimer(restanteMilisegundos,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTemporizador.setText(String.valueOf(millisUntilFinished/1000+1));

            }

            @Override
            public void onFinish() {
                finaliza();
            }
        }.start();

    }


    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnIzq:
                if(btnCambio.getText().equals(btnIzq.getText())){
                    punto();
                    colorAutomatico();
                }else{
                    finaliza();
                }
                break;
            case R.id.btnDer:
                if(btnCambio.getText().equals(btnDer.getText())){
                    punto();
                    colorAutomatico();
                }else{
                    finaliza();
                }
                break;
        }
    }

    private void punto() {
        puntuacion++;
        tvPuntuacion.setText(String.valueOf(puntuacion));
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
        btnCambio.setVisibility(View.VISIBLE);
        aleatorio = Math.random()<0.5;

        if (aleatorio){
            btnCambio.setBackgroundColor(Color.BLACK);
            btnCambio.setText("negro");
            btnCambio.setTextColor(Color.BLACK);
            btnCambio.startAnimation(animacion);

        }else{
            btnCambio.setBackgroundColor(Color.RED);
            btnCambio.setText("rojo");
            btnCambio.setTextColor(Color.RED);
            btnCambio.startAnimation(animacion);
            //TEST
        }
    }

}