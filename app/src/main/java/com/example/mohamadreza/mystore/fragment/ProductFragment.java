package com.example.mohamadreza.mystore.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mohamadreza.mystore.R;
import com.example.mohamadreza.mystore.activity.ProductActivity;
import com.example.mohamadreza.mystore.activity.ProductDetailsActivity;
import com.example.mohamadreza.mystore.models.Product;
import com.example.mohamadreza.mystore.network.Api;
import com.example.mohamadreza.mystore.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {


    private static final String ARG_PARENT_ID = "parent_id";
    private static final String ARG_LIST_TYPE = "list_type";
    private RecyclerView mRecyclerView;
    private ProductAdapter mProductAdapter;
    private Long mCategoryId;
    private Toolbar mToolbar;
    private int mListType;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance(Long categoryId, int listType) {

        Bundle args = new Bundle();
        args.putLong(ARG_PARENT_ID, categoryId);
        args.putInt(ARG_LIST_TYPE, listType);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId = getArguments().getLong(ARG_PARENT_ID);
        mListType = getArguments().getInt(ARG_LIST_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        mRecyclerView = view.findViewById(R.id.products_recycler_view);
        mToolbar = view.findViewById(R.id.toolbar);
        ((ProductActivity) getActivity()).setSupportActionBar(mToolbar);
        ((ProductActivity) getActivity()).getSupportActionBar().setTitle("لیست محصولات");


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (mCategoryId == 0) {
            switch (mListType) {
                case 1:
                    Log.d("hhhhhhhhh", "called");
                    RetrofitInstance.getInstance().create(Api.class).getProducts().enqueue(new Callback<List<Product>>() {
                        @Override
                        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                            if (response.isSuccessful()) {
                                List<Product> products = response.body();

                                mProductAdapter = new ProductAdapter(products);
                                mRecyclerView.setAdapter(mProductAdapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Product>> call, Throwable t) {

                        }
                    });

                case 2:

                    RetrofitInstance.getInstance().create(Api.class).getProducts("popularity").enqueue(new Callback<List<Product>>() {
                        @Override
                        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                            if (response.isSuccessful()) {
                                List<Product> products = response.body();

                                mProductAdapter = new ProductAdapter(products);
                                mRecyclerView.setAdapter(mProductAdapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Product>> call, Throwable t) {

                        }
                    });

                case 3:

                    RetrofitInstance.getInstance().create(Api.class).getProducts("rating").enqueue(new Callback<List<Product>>() {
                        @Override
                        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                            if (response.isSuccessful()) {
                                List<Product> products = response.body();

                                mProductAdapter = new ProductAdapter(products);
                                mRecyclerView.setAdapter(mProductAdapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Product>> call, Throwable t) {

                        }
                    });
            }

        } else {
            RetrofitInstance.getInstance().create(Api.class).getProducts().enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        List<Product> products = response.body();
                        List<Product> categoryProducts = new ArrayList<>();

                        for (int i = 0; i < products.size(); i++) {
                            if (products.get(i).getCategories().size() > 1) {
                                if (products.get(i).getCategories().get(0).getId() == mCategoryId ||
                                        products.get(i).getCategories().get(1).getId() == mCategoryId) {
                                    categoryProducts.add(products.get(i));
                                    Log.d("hhhhhhhhhhhh", products.get(i).getName());

                                }
                            }

                        }

                        mProductAdapter = new ProductAdapter(categoryProducts);
                        mRecyclerView.setAdapter(mProductAdapter);

                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    Log.d("hhhhhhhhh", "fail");
                }

            });
        }


        return view;
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
                    Intent intent = ProductDetailsActivity.newIntent(getActivity(), mProduct.getId());
                    startActivity(intent);
                }
            });
        }

        public void bind(Product product) {
            mProduct = product;
            mNameTextView.setText(product.getName());
            if (product.getImages() != null && product.getImages().size() > 0) {
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
            LayoutInflater inflater = LayoutInflater.from(getActivity());
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


//
//    private static final String ARG_PARENT_ID = "parent_id";
//    private static final String ARG_LIST_TYPE = "list_type";
//
//    private RecyclerView mRecyclerView;
//    private ProductAdapter mProductAdapter;
//    private Long mCategoryId;
//    private Toolbar mToolbar;
//    private int mListType;
//
//    public static ProductFragment newInstance(Long categoryId, int listType) {
//
//        Bundle args = new Bundle();
//        args.putLong(ARG_PARENT_ID,categoryId);
//        args.putLong(ARG_LIST_TYPE,listType);
//        ProductFragment fragment = new ProductFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//    public ProductFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mCategoryId = getArguments().getLong(ARG_PARENT_ID);
//        mListType = getArguments().getInt(ARG_LIST_TYPE);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_product, container, false);
//        mRecyclerView = view.findViewById(R.id.products_recycler_view);
//        mToolbar = view.findViewById(R.id.toolbar);
//
//        ((ProductActivity)getActivity()).setSupportActionBar(mToolbar);
//        ((ProductActivity)getActivity()).getSupportActionBar().setTitle("لیست محصولات");
//
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        if (mCategoryId == 0){
//            switch (mListType){
//                case 1:
//
//                    RetrofitInstance.getInstance().create(Api.class).getProducts().enqueue(new Callback<List<Product>>() {
//                        @Override
//                        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                            if(response.isSuccessful()){
//                                List<Product> products = response.body();
//
//                                mProductAdapter  = new ProductAdapter(products);
//                                mRecyclerView.setAdapter(mProductAdapter);
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Product>> call, Throwable t) {
//
//                        }
//                    });
//
//                case 2:
//
//                    RetrofitInstance.getInstance().create(Api.class).getProducts("popularity").enqueue(new Callback<List<Product>>() {
//                        @Override
//                        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                            if(response.isSuccessful()){
//                                List<Product> products = response.body();
//
//                                mProductAdapter  = new ProductAdapter(products);
//                                mRecyclerView.setAdapter(mProductAdapter);
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Product>> call, Throwable t) {
//
//                        }
//                    });
//
//                case 3:
//
//                    RetrofitInstance.getInstance().create(Api.class).getProducts("rating").enqueue(new Callback<List<Product>>() {
//                        @Override
//                        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                            if(response.isSuccessful()){
//                                List<Product> products = response.body();
//
//                                mProductAdapter  = new ProductAdapter(products);
//                                mRecyclerView.setAdapter(mProductAdapter);
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Product>> call, Throwable t) {
//
//                        }
//                    });
//            }
//
//        }else {
//            RetrofitInstance.getInstance().create(Api.class).getProducts().enqueue(new Callback<List<Product>>() {
//                @Override
//                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                    if (response.isSuccessful()) {
//                        List<Product> products = response.body();
//                        List<Product> categoryProducts = new ArrayList<>();
//
//                        for (int i=0; i<products.size(); i++ ){
//                            if (products.get(i).getCategories().size() > 1) {
//                                if (products.get(i).getCategories().get(0).getId() == mCategoryId ||
//                                        products.get(i).getCategories().get(1).getId() == mCategoryId) {
//                                    categoryProducts.add(products.get(i));
//                                    Log.d("hhhhhhhhhhhh",products.get(i).getName());
//
//                                }
//                            }
//
//                        }
//                        Toast.makeText(getActivity(), categoryProducts.size()+"", Toast.LENGTH_SHORT).show();
//
//                        mProductAdapter = new ProductAdapter(categoryProducts);
//                        mRecyclerView.setAdapter(mProductAdapter);
//
//                    }
//                }
//                    @Override
//                    public void onFailure(Call<List<Product>> call, Throwable t){
//                        Log.d("hhhhhhhhh", "fail");
//
//            }
//
//
//        });
//        }
//
//        return view;
//    }
//
//    private class ProductHolder extends RecyclerView.ViewHolder {
//
//        private TextView mNameTextView;
//        private ImageView mProductImageView;
//        private Product mProduct;
//
//        public ProductHolder(@NonNull View itemView) {
//            super(itemView);
//
//            mNameTextView = itemView.findViewById(R.id.product_name_text_view);
//            mProductImageView = itemView.findViewById(R.id.product_image_view);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = ProductDetailsActivity.newIntent(getActivity(),mProduct.getId());
//                    startActivity(intent);
//                }
//            });
//        }
//
//        public void bind(Product product) {
//            mProduct = product;
//            mNameTextView.setText(product.getName());
//            if(product.getImages()!= null && product.getImages().size()>0){
//                Picasso.get().load(product.getImages().get(0).getPath()).into(mProductImageView);
//            }
//        }
//    }
//
//    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
//
//        private List<Product> mProducts;
//
//        public ProductAdapter(List<Product> products) {
//            mProducts = products;
//        }
//
//        public void setProducts(List<Product> products) {
//            mProducts = products;
//        }
//
//        @NonNull
//        @Override
//        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater inflater = LayoutInflater.from(getActivity());
//            View view = inflater.inflate(R.layout.product_item, parent, false);
//            ProductHolder productHolder = new ProductHolder(view);
//            return productHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
//            Product product = mProducts.get(position);
//            holder.bind(product);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mProducts.size();
//        }
//    }

}
