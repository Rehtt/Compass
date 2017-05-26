package cn.rehtt.compass;

import android.app.LauncherActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView=(ImageView)findViewById(R.id.imageVi);

        SensorManager sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        /*
        根据方向传感器调整图片转动
         */
        SensorEventListener sensorListener=new SensorEventListener(){

            private  float predegree=0;
            public void onSensorChanged(SensorEvent event) {
                float degree=event.values[0];
                float degreecha=degree;
                float degreecha2=360-degreecha;
                if(degreecha2>360){
                    degreecha2-=360;
                }
                RotateAnimation rotateAnimation=new RotateAnimation(predegree,degreecha2, Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                rotateAnimation.setDuration(20);
                rotateAnimation.setFillAfter(true);
                imageView.startAnimation(rotateAnimation);
                predegree=degreecha2;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }


        };
        sensorManager.registerListener(sensorListener,sensor,SensorManager.SENSOR_DELAY_GAME);
    }
}


