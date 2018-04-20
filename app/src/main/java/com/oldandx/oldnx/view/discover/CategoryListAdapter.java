package com.oldandx.oldnx.view.discover;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oldandx.oldnx.R;
import com.oldandx.oldnx.databinding.ItemAdsCategoryBinding;
import com.oldandx.oldnx.view.common.baseadapter.DataBoundListAdapter;

public class CategoryListAdapter extends DataBoundListAdapter<String, ItemAdsCategoryBinding> {

    //region Private Methods
    private final android.databinding.DataBindingComponent mDataBindingComponent;
    //endregion

    CategoryListAdapter(android.databinding.DataBindingComponent dataBindingComponent) {
        this.mDataBindingComponent = dataBindingComponent;
    }

    @Override
    protected ItemAdsCategoryBinding createBinding(ViewGroup parent, int viewType) {
        ItemAdsCategoryBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_ads_category, parent, false,
                        mDataBindingComponent);

        return binding;
    }

    @Override
    protected void bind(ItemAdsCategoryBinding binding, String item, int position) {

        binding.tvCategoryText.setText(item);

        int drawableResourceId = binding.getRoot().getResources().getIdentifier(item, "drawable", binding.getRoot().getContext().getPackageName());

        binding.setResourceId(drawableResourceId);
        // binding.ivCategoryImage.setImageResource(AppUtils.getDrawableByName(binding.getRoot().getContext(), item));
    }

    @Override
    protected boolean areItemsTheSame(String oldItem, String newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    protected boolean areContentsTheSame(String oldItem, String newItem) {
        return oldItem.equals(newItem);
    }
}
