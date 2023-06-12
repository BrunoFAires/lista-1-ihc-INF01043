package com.example.exercicio2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {
    Button submit;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        editText = findViewById(R.id.text);
        editText.addTextChangedListener(textWatcher);
        onSendSecondActivity();
    }

    public void onSendSecondActivity() {

        submit = findViewById(R.id.submit);
        submit.setEnabled(false);
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String text = editText.getText().toString();

                Intent myIntent = new Intent(view.getContext(), SecondActivity.class);
                myIntent.putExtra("mytext", text);
                startActivity(myIntent);

            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String value = editText.getText().toString().trim();
            submit.setEnabled(!value.equals(""));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}