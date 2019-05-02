package com.example.mohamadreza.mystore;

import android.support.design.widget.NavigationView;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamadreza.mystore.models.Product;
import com.example.mohamadreza.mystore.network.Api;
import com.example.mohamadreza.mystore.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private RecyclerView mNewestRecyclerView;
    private RecyclerView mTopSellingRecyclerView;
    private RecyclerView mBestRecyclerView;
    private ProductAdapter mProductAdapter;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        mNewestRecyclerView = findViewById(R.id.newest_products_recycler_view);
        mTopSellingRecyclerView = findViewById(R.id.top_selling_products_recycler_view);
        mBestRecyclerView = findViewById(R.id.best_products_recycler_view);
        mNavigationView = findViewById(R.id.nav_view);

        mNewestRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mTopSellingRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mBestRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_naviga);


        RetrofitInstance.getInstance().create(Api.class).getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    List<Product> products = response.body();

                    Collections.sort(products, new Comparator<Product>(){
                        public int compare(Product a, Product b){
                            return a.getCreatedDate().compareTo(b.getCreatedDate());
                        }
                    });
                    mProductAdapter  = new ProductAdapter(products);
                    mNewestRecyclerView.setAdapter(mProductAdapter);


                    Collections.sort(products, new Comparator<Product>(){
                        public int compare(Product a, Product b){
                            return ((Integer)a.getTotalSales()).compareTo((Integer) b.getTotalSales());
                        }
                    });
                    mProductAdapter.setProducts(products);
                    mTopSellingRecyclerView.setAdapter(mProductAdapter);


                    Collections.sort(products, new Comparator<Product>(){
                        public int compare(Product a, Product b){
                            return a.getAverageRating().compareTo(b.getAverageRating());
                        }
                    });
                    mProductAdapter.setProducts(products);
                    mBestRecyclerView.setAdapter(mProductAdapter);


                    Log.d("hhhhhhhhhhh","response");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("hhhhhhhhh","fail");
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_categories :
                        Intent intent = CategoriesActivity.newIntent(MainActivity.this);
                        startActivity(intent);
                        return true;

                    case R.id.nav_newest_products :

                    case R.id.nav_top_selling_products :

                    case R.id.nav_best_products :
                }
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ProductHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private ImageView mProductImageView;
        private Product mProduct;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.product_name_text_view);
            mProductImageView = itemView.findViewById(R.id.product_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        public void bind(Product product) {
            mProduct = product;
            mNameTextView.setText(product.getName());
            if(product.getImages()!= null && product.getImages().size()>0){
                Picasso.get().load(product.getImages().get(0).getPath()).into(mProductImageView);
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
            View view = inflater.inflate(R.layout.product_item, parent, false);
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
