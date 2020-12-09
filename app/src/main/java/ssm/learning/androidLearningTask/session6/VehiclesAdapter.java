package ssm.learning.androidLearningTask.session6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ssm.learning.androidLearningTask.DrawerMenuViewHolder;
import ssm.learning.androidLearningTask.R;

public class VehiclesAdapter extends RecyclerView.Adapter<VehicleViewHolder> {
    private Vehicles mVehicles;
    public VehiclesAdapter()
    {

    }
    public void setVehicles(Vehicles vehicles){
        mVehicles=vehicles;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_list_item,parent,false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        holder.Bind(mVehicles.getVehicles().get(position));
    }

    @Override
    public int getItemCount() {
        if(mVehicles==null)
            return 0;
        return mVehicles.getVehicles().size();
    }
}
