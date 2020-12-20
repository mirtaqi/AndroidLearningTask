package ssm.learning.androidLearningTask.FinalTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ssm.learning.androidLearningTask.FinalTask.Fragments.OmdbSearchFragment;
import ssm.learning.androidLearningTask.R;

public class FinalTaskMainActivity extends AppCompatActivity {

    private Fragment[] _fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_task_main);
        ViewPager2 rootViewPager=findViewById(R.id.root_view_pager);
        MainViewPagerAdapter adapter=new MainViewPagerAdapter(this);
        _fragments=new Fragment[]{new OmdbSearchFragment(false),new OmdbSearchFragment(true)};
        adapter.setFragmentList(_fragments);
        rootViewPager.setAdapter(adapter);

        TabLayout tabLayout=findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, rootViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int i) {
                switch (i)
                {
                    case 0:
                        tab.setText("Search Omdb");
                        break;
                    case 1:
                        tab.setText("Saved Movies");
                        break;
                }
            }
        }).attach();


    }
}