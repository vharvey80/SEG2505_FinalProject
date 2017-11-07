package familytaskmanager.microso.com.familytaskmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by walid on 2017-11-06.
 */

class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ShoppingFragment();
            case 1:
                return new TasksFragment();
            case 2:
                return new PeopleFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            //
            //Your tab titles
            //
            case 0:return "Profile";
            case 1:return "Search";
            case 2: return "Contacts";
            default:return null;
        }
    }
}