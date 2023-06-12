package com.example.exercicio456;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView ligthValue;
    TextView proximityValue;
    TextView latitude;
    TextView longitude;
    private Sensor light;
    private Sensor proximity;
    private SensorManager sensorManager;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ligthValue = findViewById(R.id.ligth);
        proximityValue = findViewById(R.id.proximity);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }

        sensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (light != null) {
            sensorManager.registerListener(MainActivity.this, light,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            ligthValue.setText("Light sensor not supported");
        }

        if (light != null) {
            sensorManager.registerListener(MainActivity.this, proximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            proximityValue.setText("Proximity sensor not supported");
        }
    }

    private void startLocationUpdates() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if (sensor.getType() == Sensor.TYPE_LIGHT) {
            ligthValue.setText("Luminosidade: " + event.values[0] + "(Lux)");
        }

        if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximityValue.setText("Aproximação: " + event.values[0] + "(Cm)");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            latitude.setText("Latitude: "+location.getLatitude());
            longitude.setText("Longitude: "+location.getLongitude());

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Permissão de localização negada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }
}
