package com.xtlog.shawn.snesorpig;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MySurfaceView svCompass,svLevel;
    private TextView tvAcceleration,tvMagneticField,tvGyro,tvGravity,tvLight,tvPressure,tvTemp,tvProximity,
            tvStep;
    private Sensor sCompass,sAcceleration,sMagneticField,
            sGyro,sGravity,sLight,sPressure,sTemp,sProximity,sStep;
    public float[] accelerometerValues = new float[3],magneticFieldValues = new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        svCompass = (MySurfaceView)findViewById(R.id.svCompass);
        svLevel = (MySurfaceView)findViewById(R.id.svLevel);
        tvGyro = (TextView)findViewById(R.id.tvGyro);
        tvAcceleration = (TextView)findViewById(R.id.tvAcceleration);
        tvLight = (TextView)findViewById(R.id.tvLight);
        tvPressure = (TextView)findViewById(R.id.tvPressure);
        tvProximity = (TextView)findViewById(R.id.tvProximity);
        tvMagneticField = (TextView)findViewById(R.id.tvMagneticField);
        tvGravity = (TextView)findViewById(R.id.tvGravity);
        tvTemp = (TextView)findViewById(R.id.tvTemp);
        tvStep = (TextView)findViewById(R.id.tvStep);
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sCompass = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION); //方向
        sAcceleration = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //加速
        sMagneticField = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD); //磁场
        sGyro = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE); //陀螺仪
        sGravity = sm.getDefaultSensor(Sensor.TYPE_GRAVITY); //重力
        sLight = sm.getDefaultSensor(Sensor.TYPE_LIGHT); //光线
        sPressure = sm.getDefaultSensor(Sensor.TYPE_PRESSURE); //压力
        sTemp = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE); //温度
        sProximity = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY); //接近感应
        sStep = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER); //计步
        svCompass.thread.setType(0);
        svLevel.thread.setType(1);
        SensorEventListener selCompass = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] r = new float[9],v = new float[3];
                SensorManager.getRotationMatrix(r, null, accelerometerValues,
                        magneticFieldValues);
                SensorManager.getOrientation(r, v);
                svCompass.thread.setX(v[0]);
                svLevel.thread.setX(v[1]);
                svLevel.thread.setY(v[2]);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        SensorEventListener selCommon = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    StringBuilder sb=new StringBuilder();
                    sb.append("加速度:X");
                    sb.append(String.format("%.2f", values[0]));
                    sb.append(",Y");
                    sb.append(String.format("%.2f", values[1]));
                    sb.append(",Z");
                    sb.append(String.format("%.2f", values[2]));
                    tvAcceleration.setText(sb.toString());
                    accelerometerValues = event.values;
                } else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                    StringBuilder sb=new StringBuilder();
                    sb.append("磁场强度:X");
                    sb.append(String.format("%.2f", values[0]));
                    sb.append(",Y");
                    sb.append(String.format("%.2f", values[1]));
                    sb.append(",Z");
                    sb.append(String.format("%.2f", values[2]));
                    tvMagneticField.setText(sb.toString());
                    magneticFieldValues = event.values;
                } else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    StringBuilder sb=new StringBuilder();
                    sb.append("旋转角速度:X");
                    sb.append(String.format("%.2f", values[0]));
                    sb.append(",Y");
                    sb.append(String.format("%.2f", values[1]));
                    sb.append(",Z");
                    sb.append(String.format("%.2f", values[2]));
                    tvGyro.setText(sb.toString());
                } else if(event.sensor.getType() == Sensor.TYPE_GRAVITY) {
                    StringBuilder sb=new StringBuilder();
                    sb.append("重力:X");
                    sb.append(String.format("%.2f", values[0]));
                    sb.append(",Y");
                    sb.append(String.format("%.2f", values[1]));
                    sb.append(",Z");
                    sb.append(String.format("%.2f", values[2]));
                    tvGravity.setText(sb.toString());
                } else if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
                    tvLight.setText("当前的光线强度:"+values[0]);
                } else if(event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                    tvPressure.setText("当前的压力:"+values[0]);
                } else if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    tvTemp.setText("当前的气温:"+values[0]);
                } else if(event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    tvProximity.setText("接近感应:"+values[0]);
                } else if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                    tvStep.setText("步数:"+values[0]);
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sm.registerListener(selCompass, sCompass, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(selCommon, sGyro, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(selCommon, sAcceleration, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(selCommon, sLight, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(selCommon, sPressure, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(selCommon, sProximity, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(selCommon, sMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(selCommon, sGravity, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(selCommon, sTemp, SensorManager.SENSOR_DELAY_NORMAL);
    }

}
