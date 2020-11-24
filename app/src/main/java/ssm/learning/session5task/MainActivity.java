package ssm.learning.session5task;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ssm.learning.session5task.services.TestSerice;

public class MainActivity extends AppCompatActivity {
    private Intent _serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView drawerMenuRecyclerView= findViewById(R.id.drawer_menu_recyclerview);
        drawerMenuRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        ArrayList<DrawerMenuModel> models=new ArrayList<DrawerMenuModel>();
        models.add(new DrawerMenuModel().setIcon(R.drawable.start).setTitle("Start service").setOnClickListener(new DrawerMenuModel.OnClickListener() {
            @Override
            public void onClick() {

                if(_serviceIntent==null) {
                    _serviceIntent = new Intent(MainActivity.this, TestSerice.class);
                    startService(_serviceIntent);
                    Toast.makeText(MainActivity.this, "Service started", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Service already running...", Toast.LENGTH_LONG).show();

                }
            }
        }));
        models.add(new DrawerMenuModel().setIcon(R.drawable.stop).setTitle("Stop service").setOnClickListener(new DrawerMenuModel.OnClickListener() {
            @Override
            public void onClick() {
                if(_serviceIntent!=null)
                {
                    stopService(_serviceIntent);
                    _serviceIntent=null;
                    Toast.makeText(MainActivity.this,"Service stopped.",Toast.LENGTH_LONG).show();
                }
            }
        }));
        models.add(new DrawerMenuModel().setIcon(R.drawable.broadcast).setTitle("Send Broadcast").setOnClickListener(new DrawerMenuModel.OnClickListener() {
            @Override
            public void onClick() {
                //Toast.makeText(MainActivity.this,"Send Broadcast with action : ssm.action.MY_ACTION",Toast.LENGTH_LONG).show();
                Intent intent=new Intent();
                intent.setAction("ssm.action.MY_ACTION");
                intent.putExtra("sender","ssm");
                sendBroadcast(intent);


            }
        }));
        DrawerMenuAdapter adapter=new DrawerMenuAdapter(models);
        drawerMenuRecyclerView.setAdapter(adapter);
    }

}