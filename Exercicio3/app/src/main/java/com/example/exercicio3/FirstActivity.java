package com.example.exercicio3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.EditText;

public class FirstActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor acelerometro;

    EditText x, y, z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        x = findViewById(R.id.x);
        y = findViewById(R.id.y);
        z = findViewById(R.id.z);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acelerometro != null) {
            sensorManager.registerListener(FirstActivity.this, acelerometro,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x.setText(String.valueOf(event.values[0]));
            y.setText(String.valueOf(event.values[1]));
            z.setText(String.valueOf(event.values[2]));

            if (event.values[0] < 0) {
                Intent myIntent = new Intent(this, SecondActivity.class);
                startActivity(myIntent);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}