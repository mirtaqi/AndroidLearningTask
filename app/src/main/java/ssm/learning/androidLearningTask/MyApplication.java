package ssm.learning.androidLearningTask;

import android.app.Application;
import android.content.IntentFilter;

import ssm.learning.androidLearningTask.receivers.MyCustomReceiver;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
            super.onCreate();
        MyCustomReceiver receiver=new MyCustomReceiver();
        IntentFilter filter=new IntentFilter("ssm.action.MY_ACTION");
        filter.addAction("ssm.action.MY_ACTION");
        this.registerReceiver(receiver,filter);
    }
}
