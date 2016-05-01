package ccl2of4.plexrequests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import ccl2of4.plexrequests.events.EventBus;
import ccl2of4.plexrequests.events.ViewRequestEvent;
import ccl2of4.plexrequests.model.request.RequestRepository;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Bean
    EventBus eventBus;

    @ViewById(R.id.tabs)
    TabLayout tabLayout;

    @ViewById(R.id.viewpager)
    ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @AfterViews
    void init() {
        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Subscribe
    public void viewRequest(ViewRequestEvent event) {
        viewPager.setCurrentItem(0);
    }

}
