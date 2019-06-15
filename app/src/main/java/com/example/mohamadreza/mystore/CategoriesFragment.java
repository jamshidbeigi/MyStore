package com.example.mohamadreza.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamadreza.mystore.models.Image;

import com.example.mohamadreza.mystore.models.Category;
import com.example.mohamadreza.mystore.network.Api;
import com.example.mohamadreza.mystore.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    private static final String ARG_PARENT_ID = "parent_id";
    private RecyclerView mRecyclerView;
    private CategoryAdapter mCategoryAdapter;
    private Long mParentId;

    public static CategoriesFragment newInstance(Long parentId) {

        Bundle args = new Bundle();
        args.putLong(ARG_PARENT_ID,parentId);
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParentId = getArguments().getLong(ARG_PARENT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_categories, container, false);
        mRecyclerView = view.findViewById(R.id.categories_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RetrofitInstance.getInstance().create(Api.class).getCategories(mParentId).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()){
                    List<Category> categories = response.body();
                    mCategoryAdapter  = new CategoryAdapter(categories);
                    mRecyclerView.setAdapter(mCategoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });

        return view;
    }

    private class CategoryHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private ImageView mCategoryImageView;
        private Category mCategory;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.category_name_text_view);
            mCategoryImageView = itemView.findViewById(R.id.category_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = ProductActivity.newIntent(getActivity(),mCategory.getId(),0);
                    startActivity(intent);

                }
            });
        }

        public void bind(Category category) {
            mCategory = category;
            mNameTextView.setText(category.getName());
//            if(category.getImage()!= null)
//                Picasso.get().load(category.getImage().getPath()).into(mCategoryImageView);
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

        private List<Category> mCategories ;

        public CategoryAdapter(List<Category> categories) {
            mCategories = categories;
        }

        public void setCategories(List<Category> categories) {
            mCategories = categories;
        }

        @NonNull
        @Override
        public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.category_item, parent, false);
            CategoryHolder categoryHolder = new CategoryHolder(view);
            return categoryHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
            Category category = mCategories.get(position);
            holder.bind(category);
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }
    }

}
