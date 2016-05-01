package ccl2of4.plexrequests.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ccl2of4.plexrequests.fragment.ExistingRequestsFragment_;
import ccl2of4.plexrequests.fragment.MakeRequestsFragment_;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "New Request", "View Existing"};
    private Context context;

    public MainFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        if (0 == position) {
            return MakeRequestsFragment_.builder().build();
        }

        return ExistingRequestsFragment_.builder().build();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
