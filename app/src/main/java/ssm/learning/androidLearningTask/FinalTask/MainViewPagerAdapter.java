package ssm.learning.androidLearningTask.FinalTask;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    private Fragment[] _fragments;

    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void setFragmentList(Fragment[] fragments)
    {
        _fragments=fragments;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Fragment createFragment(int i) {
        return _fragments[i];
    }

    @Override
    public int getItemCount() {
        return _fragments.length;
    }
}
