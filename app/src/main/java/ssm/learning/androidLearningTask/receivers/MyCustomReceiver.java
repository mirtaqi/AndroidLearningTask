package ssm.learning.androidLearningTask.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyCustomReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if(intent.getAction().equals("ssm.action.MY_ACTION"))
        {
            Toast.makeText(context,"A broadcast received with action :"+intent.getAction()+" from "+intent.getStringExtra("sender"),Toast.LENGTH_LONG).show();

        }
    }
}
