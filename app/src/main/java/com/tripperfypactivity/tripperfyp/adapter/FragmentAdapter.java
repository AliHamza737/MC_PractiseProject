package com.tripperfypactivity.tripperfyp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tripperfypactivity.tripperfyp.fragments.ChatFragment;
import com.tripperfypactivity.tripperfyp.fragments.ExpencesFragment;
import com.tripperfypactivity.tripperfyp.fragments.HomeFragment;
import com.tripperfypactivity.tripperfyp.fragments.MemberFragment;


public class FragmentAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeFragment();
        } else if (position == 1) {
            return new ExpencesFragment();
        } else if (position == 2) {
            return new MemberFragment();
        } else if (position == 3) {
            return new ChatFragment();

        }else

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
