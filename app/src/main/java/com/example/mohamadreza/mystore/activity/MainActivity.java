package com.example.mohamadreza.mystore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mohamadreza.mystore.R;
import com.example.mohamadreza.mystore.models.Product;
import com.example.mohamadreza.mystore.network.Api;
import com.example.mohamadreza.mystore.network.RetrofitInstance;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private RecyclerView mBests;
    private RecyclerView mNewests;
    private RecyclerView mTopSellings;
    private TextView mNewestTextView;
    private TextView mTopSellingTextView;
    private TextView mBestTextView;
    private TextView mNewestTxtMore;
    private TextView mTopSellingTxtMore;
    private TextView mBestTxtMore;
    private ProductAdapter mProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawer = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        mNewests = findViewById(R.id.newest_products_recycler_view);
        mTopSellings = findViewById(R.id.top_selling_products_recycler_view);
        mBests = findViewById(R.id.best_products_recycler_view);
        mNavigationView = findViewById(R.id.nav_view);
        mNewestTextView = findViewById(R.id.newest_products_title);
        mTopSellingTextView = findViewById(R.id.top_selling_products_title);
        mBestTextView = findViewById(R.id.best_products_title);

        mNewestTxtMore = findViewById(R.id.newest_more);
        mTopSellingTxtMore = findViewById(R.id.top_selling_more);
        mBestTxtMore = findViewById(R.id.bests_more);

        mNewests.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mTopSellings.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBests.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("فروشگاه من");
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_naviga);

        mNewestTxtMore.setOnClickListener(v->{

            Intent intent = ProductActivity.newIntent(MainActivity.this, null, 1);
            startActivity(intent);
        });

        mTopSellingTxtMore.setOnClickListener(v->{

            Intent intent = ProductActivity.newIntent(MainActivity.this, null, 2);
            startActivity(intent);
        });

        mBestTxtMore.setOnClickListener(v->{

                Intent intent = ProductActivity.newIntent(MainActivity.this, null, 3);
                startActivity(intent);
        });


        RetrofitInstance.getInstance().create(Api.class).getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> newestProducts = response.body();

                    Collections.sort(newestProducts, (a, b) -> a.getCreatedDate().compareTo(b.getCreatedDate()));
                    mProductAdapter = new ProductAdapter(newestProducts);
                    mNewests.setAdapter(mProductAdapter);

                    mNewests.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (!mNewests.canScrollHorizontally(1)) {

                            }
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        RetrofitInstance.getInstance().create(Api.class).getProducts("popularity").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> topSellingProducts = response.body();

                    mProductAdapter = new ProductAdapter(topSellingProducts);
                    mTopSellings.setAdapter(mProductAdapter);

                }
            }

            public void onFailure(Call<List<Product>> call, Throwable t) {


            }

        });

        RetrofitInstance.getInstance().create(Api.class).getProducts("rating").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> bestProducts = response.body();

                    mProductAdapter = new ProductAdapter(bestProducts);
                    mBests.setAdapter(mProductAdapter);

                }
            }

            public void onFailure(Call<List<Product>> call, Throwable t) {


            }

        });


        mNavigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {

                case R.id.nav_categories:

                    Intent intent = CategoriesActivity.newIntent(MainActivity.this);
                    startActivity(intent);
                    return true;

                case R.id.nav_newest_products:
                    Intent intent1 = ProductActivity.newIntent(MainActivity.this, null, 1);
                    startActivity(intent1);
                    return true;

                case R.id.nav_top_selling_products:
                    Intent intent2 = ProductActivity.newIntent(MainActivity.this, null, 2);
                    startActivity(intent2);
                    return true;

                case R.id.nav_best_products:
                    Intent intent3 = ProductActivity.newIntent(MainActivity.this, null, 3);
                    startActivity(intent3);
                    return true;
            }
            return false;
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class ProductHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private TextView mPrice;
        private ImageView mProductImageView;
        private Product mProduct;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.product_name_text_view);
            mProductImageView = itemView.findViewById(R.id.product_image_view);
            mPrice = itemView.findViewById(R.id.product_price);

            itemView.setOnClickListener(v -> {

                Intent intent = ProductDetailsActivity.newIntent(MainActivity.this, mProduct.getId());
                startActivity(intent);
            });
        }

        public void bind(Product product) {
            mProduct = product;
            mNameTextView.setText(mProduct.getName());
            mPrice.setText(mProduct.getPrice());
            if (mProduct.getImages() != null && mProduct.getImages().size() > 0) {
                Picasso.get().load(mProduct.getImages().get(0).getPath()).into(mProductImageView);
            }
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

        private List<Product> mProducts;

        public ProductAdapter(List<Product> products) {
            mProducts = products;
        }

        public void setProducts(List<Product> products) {
            mProducts = products;
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View view = inflater.inflate(R.layout.item_horizontal_product, parent, false);
            ProductHolder productHolder = new ProductHolder(view);
            return productHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            Product product = mProducts.get(position);
            holder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }
    }


}
