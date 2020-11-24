package ssm.learning.session5task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrawerMenuAdapter extends RecyclerView.Adapter<DrawerMenuViewHolder> {
    private ArrayList<DrawerMenuModel> mMenuModels;
    public DrawerMenuAdapter(ArrayList<DrawerMenuModel> menuModels)
    {
        mMenuModels=menuModels;
    }
    @NonNull
    @Override
    public DrawerMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_menu_list_item,parent,false);
        return new DrawerMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerMenuViewHolder holder, int position) {
        holder.Bind(mMenuModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mMenuModels.size();
    }
}
