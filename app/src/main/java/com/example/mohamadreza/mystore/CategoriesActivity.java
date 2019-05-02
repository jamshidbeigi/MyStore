package com.example.mohamadreza.mystore;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.example.mohamadreza.mystore.models.Category;
import com.example.mohamadreza.mystore.network.Api;
import com.example.mohamadreza.mystore.network.RetrofitInstance;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {

    private ViewPager mCategoryViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    private List<Category> mCategories;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, CategoriesActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        mCategoryViewPager = findViewById(R.id.category_view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        RetrofitInstance.getInstance().create(Api.class).getCategories((long) 0).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()){
                    mCategories = response.body();
                    setViewPager();

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });


        mTabLayout.setupWithViewPager(mCategoryViewPager);

    }

    private void setViewPager() {
        mCategoryViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {

                switch (i){
                    case 0:
                        return CategoriesFragment.newInstance(mCategories.get(0).getId());
                    case 1:
                        return CategoriesFragment.newInstance(mCategories.get(1).getId());
                    case 2:
                        return CategoriesFragment.newInstance(mCategories.get(2).getId());
                    case 3:
                        return CategoriesFragment.newInstance(mCategories.get(3).getId());
                }

                return null;

            }

            @Override
            public int getCount() {
                return mCategories.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                if (position==0)
                    return mCategories.get(0).getName();
                if (position==1)
                    return mCategories.get(1).getName();
                if (position==2)
                    return mCategories.get(2).getName();
                if (position==3)
                    return mCategories.get(3).getName();

                return super.getPageTitle(position);
            }
        });

        mCategoryViewPager.setOffscreenPageLimit(5);
    }
}