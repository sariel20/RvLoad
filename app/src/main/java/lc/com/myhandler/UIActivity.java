package lc.com.myhandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by LiangCheng on 2017/11/22.
 * <p>
 * 更新UI方式
 */

public class UIActivity extends Activity {
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        textView = (TextView) findViewById(R.id.tv);

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            textView.setText("update");
        }
    };

    private void update1() {
        handler.sendEmptyMessage(1);
    }

    private void update2() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("update");
            }
        });
    }

    private void update3() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText("update");
            }
        });
    }

    private void update4() {
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("update");
            }
        });
    }
}
