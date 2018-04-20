/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oldandx.oldnx.view.common.baseadapter;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * A generic RecyclerView adapter that uses Data Binding & DiffUtil.
 *
 * @param <T> Type of the mItems in the list
 * @param <V> The of the ViewDataBinding
 */
public abstract class DataBoundListAdapter<T, V extends ViewDataBinding>
        extends RecyclerView.Adapter<DataBoundViewHolder<V>> {

    @Nullable
    private List<T> mItems;
    // each time data is set, we update this variable so that if DiffUtil calculation returns
    // after repetitive updates, we can ignore the old calculation
    private int mDataVersion = 0;

    @Override
    public final DataBoundViewHolder<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        V binding = createBinding(parent, viewType);
        return new DataBoundViewHolder<>(binding);
    }

    protected abstract V createBinding(ViewGroup parent, int viewType);

    @Override
    public final void onBindViewHolder(DataBoundViewHolder<V> holder, int position) {
        //noinspection ConstantConditions
        bind(holder.mBinding, null != mItems && position < mItems.size() ? mItems.get(position) : null, position);
        holder.mBinding.executePendingBindings();
    }

    public T getItem(int position) {
        return null != mItems ? mItems.get(position) : null;
    }

    public void removeItem(int position) {
        if (null != mItems) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void restoreItem(T item, int position) {
        if (null != mItems) {
            mItems.add(position, item);
            notifyItemInserted(position);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    public void replace(List<T> update) {
        mDataVersion++;
        if (mItems == null) {
            if (update == null) {
                return;
            }
            mItems = update;
            notifyDataSetChanged();
        } else if (update == null) {
            int oldSize = mItems.size();
            mItems = null;
            notifyItemRangeRemoved(0, oldSize);
        } else {
            final int startVersion = mDataVersion;
            final List<T> oldItems = mItems;
            new AsyncTask<Void, Void, DiffUtil.DiffResult>() {
                @Override
                protected DiffUtil.DiffResult doInBackground(Void... voids) {
                    return DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return oldItems.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return update.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            T oldItem = oldItems.get(oldItemPosition);
                            T newItem = update.get(newItemPosition);
                            return DataBoundListAdapter.this.areItemsTheSame(oldItem, newItem);
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            T oldItem = oldItems.get(oldItemPosition);
                            T newItem = update.get(newItemPosition);
                            return DataBoundListAdapter.this.areContentsTheSame(oldItem, newItem);
                        }
                    });
                }

                @Override
                protected void onPostExecute(DiffUtil.DiffResult diffResult) {
                    if (startVersion != mDataVersion) {
                        // ignore update
                        return;
                    }
                    mItems = update;
                    diffResult.dispatchUpdatesTo(DataBoundListAdapter.this);

                }
            }.execute();
        }
    }

    protected abstract void bind(V binding, T item, int position);

    protected abstract boolean areItemsTheSame(T oldItem, T newItem);

    protected abstract boolean areContentsTheSame(T oldItem, T newItem);

    @Nullable
    public List<T> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }
}
