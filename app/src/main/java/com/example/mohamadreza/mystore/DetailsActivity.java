package com.example.mohamadreza.mystore;

import android.support.v4.app.Fragment;

public class DetailsActivity extends SingleFragmentActivity{

    @Override
    public Fragment createFragment() {
        return DetailsFragment.newInstance();
    }

}



