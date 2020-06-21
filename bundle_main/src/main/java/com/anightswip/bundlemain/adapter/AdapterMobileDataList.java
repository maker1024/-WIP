package com.anightswip.bundlemain.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anightswip.bundlemain.databinding.BundleMainMobiledataItemBinding;
import com.anightswip.bundlemain.viewmodel.BViewMobileDataList;
import com.anightswip.bundleplatform.baselayer.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

public class AdapterMobileDataList extends RecyclerView.Adapter<AdapterMobileDataList.ViewHolder>
        implements View.OnClickListener {

    private List<BViewMobileDataList.BViewMobileData> itemDatas;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BundleMainMobiledataItemBinding binding =
                BundleMainMobiledataItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false);
        return new ViewHolder(binding);
    }

    public void setData(ArrayList<BViewMobileDataList.BViewMobileData> data) {
        this.itemDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setBv(itemDatas.get(position));
        holder.binding.setClick(this);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return itemDatas == null ? 0 : itemDatas.size();
    }

    @Override
    public void onClick(View v) {
        ToastView.show("该年度有季度下滑");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public BundleMainMobiledataItemBinding binding;

        ViewHolder(BundleMainMobiledataItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
