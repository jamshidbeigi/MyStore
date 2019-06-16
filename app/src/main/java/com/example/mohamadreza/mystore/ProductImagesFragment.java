package com.example.mohamadreza.mystore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductImagesFragment extends Fragment {

    private static final String ARG_IMAGE_PATH = "image_path";

    private ImageView mImageView;

    private String mImagePath;

    public static ProductImagesFragment newInstance(String imagePath) {

        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_PATH,imagePath);
        ProductImagesFragment fragment = new ProductImagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductImagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImagePath = getArguments().getString(ARG_IMAGE_PATH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_images, container, false);
        mImageView = view.findViewById(R.id.product_image);

        if(mImagePath != null){
            Picasso.get().load(mImagePath).into(mImageView);
        }

        return view;
    }

}