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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int puntuacion;
    private DBHelper db =null;
    private Animation animacion;

    private TextView tvTemporizador,tvPuntuacion,tvTiempo;
    private CountDownTimer countDownTimer,countDownTimer2;
    private long duracion = 10000; // 10 Segundos.
    private long tresSegundos = 3000;

    private ImageView redStar, blueStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        animacion = AnimationUtils.loadAnimation(this, R.anim.scale);

        tvPuntuacion = findViewById(R.id.tvPuntuacion);

        tvTemporizador = findViewById(R.id.tvTemporizador);
        tvTiempo = findViewById(R.id.tvTiempo);

        redStar = findViewById(R.id.redStar);
        blueStar = findViewById(R.id.blueStar);

        cuentaAtras();
    }
    private void empiezar(){
        tiempoPartida();
        timer();
        colorAutomatico();
    }

    private long tiempoPartida() {

        boolean turbo = getIntent().getBooleanExtra("turbo",false);
        if(turbo){
            duracion = 5000;
        }else{
            duracion = 10000;
        }


        return duracion;
    }

    private void cuentaAtras(){

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
        countDownTimer = new CountDownTimer(duracion,1000) {
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
            case R.id.btnRed:
                if(redStar.getVisibility()==View.VISIBLE){
                    punto();
                    colorAutomatico();
                }else {
                    finaliza();
                }
                break;
            case R.id.btnblue:
                if(blueStar.getVisibility()==View.VISIBLE){
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
        Random rd = new Random();
        redStar.setVisibility(View.INVISIBLE);
        blueStar.setVisibility(View.INVISIBLE);
        redStar.clearAnimation();
        blueStar.clearAnimation();
        if (rd.nextBoolean()){
            blueStar.setVisibility(View.VISIBLE);
            blueStar.startAnimation(animacion);
        }else{
            redStar.setVisibility(View.VISIBLE);
            redStar.startAnimation(animacion);
        }

    }
}