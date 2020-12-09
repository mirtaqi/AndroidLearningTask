package ssm.learning.androidLearningTask;

public class DrawerMenuModel {
    private String mTitle;
    private OnClickListener mOnClickListener;
    private int _icon;
    public String getTitle()
    {
        return mTitle;
    }
    public DrawerMenuModel  setTitle(String title)
    {
        mTitle=title;
        return this;
    }

    public DrawerMenuModel setOnClickListener(OnClickListener onClickListener)
    {
        mOnClickListener=onClickListener;
        return this;
    }
    public void clicked()
    {
        if(mOnClickListener!=null)
            mOnClickListener.onClick();
    }
    public interface OnClickListener{
        public void onClick();
    }
    public DrawerMenuModel setIcon(int icon_id)
    {
        _icon=icon_id;
        return this;
    }
    public int getIcon()
    {
        return _icon;
    }
}
