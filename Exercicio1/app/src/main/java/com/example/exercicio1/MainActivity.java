package com.example.exercicio1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText firstValue, secondValue;
    Button sumButton;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstValue = findViewById(R.id.firstValue);
        secondValue = findViewById(R.id.secondValue);
        sumButton = findViewById(R.id.sumButtom);
        result = findViewById(R.id.result);
        sumButton.setEnabled(false);

        firstValue.addTextChangedListener(textWatcher);
        secondValue.addTextChangedListener(textWatcher);
    }

    public void handleSumClick(View view) {
        Double sumResult = Double.parseDouble(firstValue.getText().toString()) + Double.parseDouble(secondValue.getText().toString());
        result.setText(String.format("%.2f", sumResult));
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String firstNumber = firstValue.getText().toString().trim();
            String secondNumber = secondValue.getText().toString().trim();
            sumButton.setEnabled(!firstNumber.equals("") && !secondNumber.equals(""));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}