package ssm.learning.session5task.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TestSerice extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        start();
        return super.onStartCommand(intent, flags, startId);

    }
    private boolean mStop=false;
    private void start()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!mStop) {
                    Toast.makeText(getApplicationContext(), "Service is running...", Toast.LENGTH_SHORT).show();
                    start();
                }
            }
        },5000);
    }

    @Override
    public void onDestroy() {
        mStop=true;
        super.onDestroy();
    }
}
