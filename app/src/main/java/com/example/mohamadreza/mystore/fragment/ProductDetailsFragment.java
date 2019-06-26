package com.example.mohamadreza.mystore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mohamadreza.mystore.R;
import com.example.mohamadreza.mystore.models.Product;
import com.example.mohamadreza.mystore.network.Api;
import com.example.mohamadreza.mystore.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {


    private static final String DIALOG_PRODUCT_ATTRIBUTES = "product_attributes";
    private static final String ARG_PRODUCT_ID = "product_id";
    private ViewPager mViewPager;
    private TextView mProductName;
    private TextView mProductPrice;
    private TextView mProductDescription;
    private TextView mMore;
    private Toolbar mToolbar;
    private Button mAttributeButton;
    private List<Product> mProducts;
    private Product mProduct;
    private Long mProductId;


    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance(Long productId) {
        Bundle args = new Bundle();
        args.putLong(ARG_PRODUCT_ID, productId);
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductId = getArguments().getLong(ARG_PRODUCT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        mViewPager = view.findViewById(R.id.product_images_view_pager);
        mProductName = view.findViewById(R.id.product_name);
        mProductPrice = view.findViewById(R.id.product_price);
        mProductDescription = view.findViewById(R.id.product_description);
        mToolbar = view.findViewById(R.id.toolbar);
        mAttributeButton = view.findViewById(R.id.product_attribute);
        mMore = view.findViewById(R.id.more_txt);

        mMore.setOnClickListener(v->{

            if(mMore.getText().toString().equals("ادامه متن")){
            mProductDescription.setMaxLines(Integer.MAX_VALUE);
            mMore.setText("خلاصه متن");
            }
            else{
                mProductDescription.setMaxLines(5);
                mMore.setText("ادامه متن");
            }
        });



            //        ((ProductDetailsActivity)getActivity()).setSupportActionBar(mToolbar);


        RetrofitInstance.getInstance().create(Api.class).getProduct(mProductId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    mProduct = response.body();
                    mProductName.setText(mProduct.getName());
                    mProductPrice.setText("قیمت :    " + mProduct.getPrice());

                    String description = mProduct.getDescription();
                    description = description.replace("</p>","");
                    description = description.replace("<p>","");

                    mProductDescription.setText(description);


                    mViewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
                        @Override
                        public Fragment getItem(int i) {
                            String imagePath = mProduct.getImages().get(i).getPath();
                            return ProductImagesFragment.newInstance(imagePath);
                        }

                        @Override
                        public int getCount() {
                            return mProduct.getImages().size();
                        }
                    });

//                    mViewPager.setPageMargin(
//                            getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));

                    mAttributeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ProductAttributesDialogFragment productAttributesDialogFragment =
                                    ProductAttributesDialogFragment.newInstance(mProduct.getId());

                            productAttributesDialogFragment.show(getFragmentManager(), DIALOG_PRODUCT_ATTRIBUTES);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

        return view;
    }

}
