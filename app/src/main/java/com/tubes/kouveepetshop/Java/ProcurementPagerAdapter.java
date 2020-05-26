package com.tubes.kouveepetshop.Java;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tubes.kouveepetshop.Fragment.ProcurementCanceledFragment;
import com.tubes.kouveepetshop.Fragment.ProcurementDoneFragment;
import com.tubes.kouveepetshop.Fragment.ProcurementProcessFragment;

public class ProcurementPagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public ProcurementPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new ProcurementCanceledFragment();
            case 1:
                return new ProcurementProcessFragment();
            case 2:
                return new ProcurementDoneFragment();
            default:
                return null;
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return numOfTabs;
    }
}
