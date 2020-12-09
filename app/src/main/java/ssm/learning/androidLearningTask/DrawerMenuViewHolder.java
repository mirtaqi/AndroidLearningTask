package ssm.learning.androidLearningTask;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DrawerMenuViewHolder extends RecyclerView.ViewHolder {
    private TextView mTextView;
    private DrawerMenuModel mMenuModel;
    private ImageView mIcon;
    public DrawerMenuViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView=(TextView)itemView.findViewById(R.id.text);
        mIcon=itemView.findViewById(R.id.icon);

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMenuModel!=null)
                    mMenuModel.clicked();
            }
        };
        mTextView.setOnClickListener(listener);
        mIcon.setOnClickListener(listener);

    }
    public void Bind(DrawerMenuModel menuModel)
    {
        mMenuModel=menuModel;
        mTextView.setText(menuModel.getTitle());
        if(menuModel.getIcon()>0)
            mIcon.setImageDrawable(this.itemView.getContext().getDrawable(menuModel.getIcon()));
    }

}
