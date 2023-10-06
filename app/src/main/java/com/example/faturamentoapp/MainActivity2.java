package com.example.faturamentoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity2 extends AppCompatActivity {

    private Button mButton;
    private TextView mTextView;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mEditText = findViewById(R.id.editTextPersonalizarNomeEmpresa);

        mButton = findViewById(R.id.buttonPersonalizar);
        mButton.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               String nomeEmpresa = mEditText.getText().toString();

               if (!nomeEmpresa.isEmpty()){
                   getSharedPreferences(MainActivity.ARQUIVO_MEUS_DADOS,Context.MODE_PRIVATE).edit().putString("NomeEmpresa",nomeEmpresa).apply();
               }

               Intent intent = new Intent(getBaseContext(),MainActivity.class);
               startActivity(intent);
           }
        });
    }

}