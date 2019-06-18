package com.example.mohamadreza.mystore.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.mohamadreza.mystore.fragment.ProductDetailsFragment;

public class ProductDetailsActivity extends SingleFragmentActivity {


    private static final String EXTRA_PRODUCT_ID = "product_id";

    public static Intent newIntent(Context context, Long productId) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        Long productId = getIntent().getLongExtra(EXTRA_PRODUCT_ID, 0);
        return ProductDetailsFragment.newInstance(productId);
    }

}
