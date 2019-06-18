package com.example.mohamadreza.mystore.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.mohamadreza.mystore.fragment.ProductFragment;

public class ProductActivity extends SingleFragmentActivity {

    private static final String EXTRA_CATEGORY_ID = "category_id";
    private static final String EXTRA_LIST_TYPE = "list_type";

    public static Intent newIntent(Context context, Long categoryId, int listType) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra(EXTRA_LIST_TYPE, listType);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        Long categoryId = getIntent().getLongExtra(EXTRA_CATEGORY_ID, 0);
        int listType = getIntent().getIntExtra(EXTRA_LIST_TYPE, 0);
        return ProductFragment.newInstance(categoryId, listType);
    }
}
