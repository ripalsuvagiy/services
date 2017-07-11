package com.services.adapter;

/**
 * Created by Ripal on 10/22/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.services.fragment.FireExtinguishersDetails;
import com.services.fragment.PersonalDetail;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PersonalDetail tab1 = new PersonalDetail();
                return tab1;
            case 1:
                FireExtinguishersDetails tab2 = new FireExtinguishersDetails();
                return tab2;
//

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}