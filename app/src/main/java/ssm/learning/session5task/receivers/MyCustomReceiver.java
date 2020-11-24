package ssm.learning.session5task.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.RenderNode;
import android.os.Handler;
import android.widget.Toast;

import ssm.learning.session5task.MainActivity;

public class MyCustomReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if(intent.getAction().equals("ssm.action.MY_ACTION"))
        {
            Toast.makeText(context,"A broadcast received with action :"+intent.getAction()+" from "+intent.getStringExtra("sender"),Toast.LENGTH_LONG).show();

        }
    }
}
