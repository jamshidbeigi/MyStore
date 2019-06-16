package com.example.mohamadreza.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ProductActivity extends SingleFragmentActivity {

    private static final String EXTRA_CATEGORY_ID = "category_id";
    private static final String EXTRA_LIST_TYPE = "list_type";


    public static Intent newIntent(Context context, Long categoryId, int listType){
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra(EXTRA_LIST_TYPE,listType);
        intent.putExtra(EXTRA_CATEGORY_ID,categoryId);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        Long categoryId = getIntent().getLongExtra(EXTRA_CATEGORY_ID,0);
        int listType = getIntent().getIntExtra(EXTRA_LIST_TYPE,0);
        return ProductFragment.newInstance(categoryId,listType);    }
}
