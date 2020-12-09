package ssm.learning.androidLearningTask.session6;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import ssm.learning.androidLearningTask.R;

public class VehicleViewHolder extends RecyclerView.ViewHolder {
    private ImageView mVehicleImage;
    private TextView mVehicleType;
    private TextView mVehicleLat;
    private TextView mVehicleLong;
    private TextView mVehicleBearing;
    private Vehicle model;
    public VehicleViewHolder(@NonNull View itemView) {
        super(itemView);
        mVehicleImage=itemView.findViewById(R.id.vehicle_image);
        mVehicleType=itemView.findViewById(R.id.vehicle_type);
        mVehicleLat=itemView.findViewById(R.id.vehicle_lat);
        mVehicleLong=itemView.findViewById(R.id.vehicle_lng);
        mVehicleBearing=itemView.findViewById(R.id.vehicle_bearing);
    }

    public void Bind(Vehicle vehicle) {
        model=vehicle;
        if(model!=null)
        {
            Glide.with(this.itemView).load(model.getImageUrl()).into(mVehicleImage);
            mVehicleType.setText(model.getType());
            mVehicleLat.setText(model.getLat().toString());
            mVehicleLong.setText(model.getLng().toString());
            mVehicleBearing.setText(model.getBearing().toString());
        }
    }
}
