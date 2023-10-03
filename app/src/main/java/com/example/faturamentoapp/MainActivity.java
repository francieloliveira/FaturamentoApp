package com.example.faturamentoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String ARQUIVO_MEUS_DADOS = "meusDados";
    private NumberPicker mNumberPicker;
    private Button mButton;
    private RadioGroup mRadioGroup;
    private TextView mTextViewSaldo;
    private EditText mEditTextValor;

    int checkedRadioid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Botão de ação
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEditTextValor.getText().toString().isEmpty()) {
                    float valor = Float.parseFloat(mEditTextValor.getText().toString());
                    int ano = mNumberPicker.getValue();

                    if (checkedRadioid == R.id.radioButtonSalvar) {
                        adicionarValor(ano,valor);
                    } else if (checkedRadioid == R.id.radioButtonExcluir){
                        excluirValor(ano,valor);
                    }
                    exibirSaldo(ano);
                }
            }
        });

        //Datas
        mNumberPicker = findViewById(R.id.numberPicker);
        mNumberPicker.setMinValue(2015);
        mNumberPicker.setMaxValue(2023);
        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                exibirSaldo(newVal);
            }
        });

        //
        mRadioGroup = findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checkedRadioid = mRadioGroup.getCheckedRadioButtonId();
            }
        });

        //Valor a ser editado
        mEditTextValor = findViewById(R.id.editTextValor);

        //Saldo
        mTextViewSaldo = findViewById(R.id.textViewSaldo);
    }

    private void adicionarValor(int ano, float valor){
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float valAtual = sharedPreferences.getFloat(String.valueOf(ano),0);
        float novoValor = valAtual + valor;
        sharedPreferences.edit().putFloat(String.valueOf(ano), novoValor).apply();
        exibirSaldo(ano);
        Toast.makeText(getApplicationContext(), "Valor Adicionado: R$ "+valor, Toast.LENGTH_SHORT).show();
    }



    private void excluirValor(int ano, float valor) {
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        String chaveAno = String.valueOf(ano);

        float valAtual = sharedPreferences.getFloat(chaveAno, 0);
        float novoValor = valAtual - valor;

        if (novoValor < 0) {
            Toast.makeText(getApplicationContext(), "Não é possível ter um valor negativo.", Toast.LENGTH_SHORT).show();
        } else {
            sharedPreferences.edit().putFloat(chaveAno, novoValor).apply();
            exibirSaldo(ano);
            Toast.makeText(getApplicationContext(), "Valor Excluído: R$ " + valor, Toast.LENGTH_SHORT).show();
        }
    }


    private void exibirSaldo(int ano){
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float valAtual = sharedPreferences.getFloat(String.valueOf(ano),0);
        mTextViewSaldo.setText(String.format("%s %s", getString(R.string.RS), valAtual));
    }

}