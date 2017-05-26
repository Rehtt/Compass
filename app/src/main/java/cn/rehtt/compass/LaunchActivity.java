package cn.rehtt.compass;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

public class LaunchActivity extends Activity {
    Button button;
    static int o=0;   //若点击了“跳过”按钮则不再执行5秒自动跳转
    int i=5;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉顶部状态栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);   //去掉顶部标题栏

        setContentView(R.layout.launch);

/*
设置跳转按钮，按钮监听
 */
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                o = 1;
                Intent intent = new Intent();
                intent.setClass(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        textView=(TextView)findViewById(R.id.textView);
        new Thread(new MyThread()).start();





/*
设置5秒自动跳转，使用线程
 */
        Integer time = 5000;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (o == 0) {
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, time);

        textView = (TextView) findViewById(R.id.textView);
        handler.sendEmptyMessageDelayed(0,1000);



    }

    /*
    设置倒计时
     */

    final Handler handler = new Handler(){          // handle
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 1:
                    i--;

                    textView.setText("" + i);
            }
            super.handleMessage(msg);
        }
    };

    public class MyThread implements Runnable {      // thread
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);     // sleep 1000ms
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                }


            }
        }
    }}

