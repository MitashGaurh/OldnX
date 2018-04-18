package com.oldandx.oldnx.binding;

import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Binding adapters that work with a mFragment instance.
 */
public class FragmentBindingAdapters {

    private final Fragment mFragment;

    public FragmentBindingAdapters(Fragment fragment) {
        this.mFragment = fragment;
    }

    @BindingAdapter("imageUrl")
    public void bindImage(ImageView imageView, String url) {
        Glide.with(mFragment).load(url).into(imageView);
    }
}
