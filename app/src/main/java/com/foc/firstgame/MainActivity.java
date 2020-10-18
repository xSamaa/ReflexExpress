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

public class MainActivity extends AppCompatActivity {

    private Button btnIzq, btnDer;
    private boolean aleatorio;
    private int puntuacion;
    private DBHelper db =null;
    private Animation animacion;

    private TextView tvTemporizador,tvPuntuacion,tvTiempo;
    private CountDownTimer countDownTimer,countDownTimer2;
    private long restanteMilisegundos = 10000; // 10 Segundos.
    private long tresSegundos = 3000;

    private ImageView redStar, blueStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIzq = findViewById(R.id.btnIzq);
        btnDer = findViewById(R.id.btnDer);

        animacion = AnimationUtils.loadAnimation(this, R.anim.scale);

        tvPuntuacion = findViewById(R.id.tvPuntuacion);

        tvTemporizador = findViewById(R.id.tvTemporizador);
        tvTiempo = findViewById(R.id.tvTiempo);

        redStar = findViewById(R.id.redStar);
        blueStar = findViewById(R.id.blueStar);

        cuentaAtras();
    }
    private void empiezar(){
        timer();
        colorAutomatico();
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
                if(redStar.getVisibility()==View.VISIBLE){
                    punto();
                    colorAutomatico();
                }else {
                    finaliza();
                }
                break;
            case R.id.btnDer:
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
        blueStar.setVisibility(View.INVISIBLE);
        redStar.setVisibility(View.INVISIBLE);
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
        redStar.setVisibility(View.INVISIBLE);
        blueStar.setVisibility(View.INVISIBLE);

        if (aleatorio){
            blueStar.setVisibility(View.VISIBLE);
            blueStar.startAnimation(animacion);

        }else{
            redStar.setVisibility(View.VISIBLE);
            redStar.startAnimation(animacion);
        }

    }
}