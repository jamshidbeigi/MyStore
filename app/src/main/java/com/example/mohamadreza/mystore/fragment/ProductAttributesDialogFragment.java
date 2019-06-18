package com.example.mohamadreza.mystore.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.mohamadreza.mystore.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductAttributesDialogFragment extends DialogFragment {

    private static final String ARG_PRODUCT_ID = "product_id";

    private Long mProductId;

    public ProductAttributesDialogFragment() {
        // Required empty public constructor
    }

    public static ProductAttributesDialogFragment newInstance(Long productId) {

        Bundle args = new Bundle();
        args.putLong(ARG_PRODUCT_ID, productId);
        ProductAttributesDialogFragment fragment = new ProductAttributesDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
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
        View view = inflater.inflate(R.layout.fragment_product_attributes_dialog, container, false);
        return view;
    }


}
