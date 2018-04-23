package com.oldandx.oldnx.view.common.baseadapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * A generic ViewHolder that works with a {@link ViewDataBinding}.
 *
 * @param <T> The type of the ViewDataBinding.
 */
public class DataBoundViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T mBinding;

    DataBoundViewHolder(T binding) {
        super(binding.getRoot());
        this.mBinding = binding;
    }
}
