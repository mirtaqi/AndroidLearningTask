package ssm.learning.androidLearningTask.session6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.Reader;
import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;
import ssm.learning.androidLearningTask.R;


public class Session6TaskActivity extends AppCompatActivity {

    private RecyclerView mVehicleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session6_task);

        //init recycle view
        mVehicleList=findViewById(R.id.vehicle_list);
        mVehicleList.setLayoutManager(new LinearLayoutManager(Session6TaskActivity.this));

        final VehiclesAdapter adapter=new VehiclesAdapter();
        mVehicleList.setAdapter(adapter);

        AsyncHttpClient client=new AsyncHttpClient();
        Toast.makeText(this,"trying to get vehicle list from https://pouyaheydari.com/vehicles.json",Toast.LENGTH_LONG);
        client.get("https://pouyaheydari.com/vehicles.json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson gson=new Gson();
                String str=new String(responseBody, StandardCharsets.US_ASCII);
                Vehicles vehicles= gson.fromJson(str,Vehicles.class);
                adapter.setVehicles(vehicles);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(Session6TaskActivity.this,"Error getting response from source",Toast.LENGTH_LONG).show();
            }
        });
    }

}