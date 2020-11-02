package com.foc.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.Console;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements OnClickListener {

    private EditText edUser,edPass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUser = findViewById(R.id.edUser);
        edPass = findViewById(R.id.edPass);
        btnLogin = findViewById(R.id.btnLogin);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                buscar(Integer.parseInt(edPass.getText().toString()));

              //  Intent intent = new Intent(this,Menu.class);
               // startActivity(intent);
                //break;
        }
    }

    private void buscar(Integer codigo){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.21:8080/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ServiceRest serviceRest = retrofit.create(ServiceRest.class);
        Call<Users> call = serviceRest.find(codigo);


        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                try {
                    if (!response.isSuccessful()){
                        Users u = response.body();
                        edUser.setText(u.getName());
                    Toast.makeText(Login.this,"Encontrado",Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception ex){
                    Toast.makeText(Login.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(Login.this, "Error de conexion" ,Toast.LENGTH_SHORT).show();
            }
        });
    }
}