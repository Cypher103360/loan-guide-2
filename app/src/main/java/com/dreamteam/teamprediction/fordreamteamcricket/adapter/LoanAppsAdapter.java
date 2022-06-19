package com.dreamteam.teamprediction.fordreamteamcricket.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamteam.teamprediction.R;
import com.dreamteam.teamprediction.databinding.AdLayoutBinding;
import com.dreamteam.teamprediction.fordreamteamcricket.models.LoanAppModel;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.Prevalent;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.ShowAds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;

public class LoanAppsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW = 0;
    private static final int AD_VIEW = 1;
    private static final int ITEM_FEED_COUNT = 3;
    List<LoanAppModel> loanAppModelList = new ArrayList<>();
    Activity context;
    LoanAppInterface loanAppInterface;
    ShowAds showAds;

    public LoanAppsAdapter(Activity context, LoanAppInterface loanAppInterface) {
        this.context = context;
        this.loanAppInterface = loanAppInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item_layout, parent, false);
            return new ItemViewHolder(view);

        } else if (viewType == AD_VIEW) {
            View view = LayoutInflater.from(context).inflate(R.layout.ad_layout, parent, false);
            return new AdViewHolder(view);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        if (holder.getItemViewType() == ITEM_VIEW) {
            int position = pos - Math.round(pos / ITEM_FEED_COUNT);
            showAds = new ShowAds();

            LoanAppModel loanAppModel = loanAppModelList.get(position);
            ((ItemViewHolder) holder).appName.setText(loanAppModel.getTitle());
            Glide.with(context).load("https://gedgetsworld.in/Loan_App/loan_app_images/"
                    + loanAppModel.getImg()).into(((ItemViewHolder) holder).appIcon);

            ((ItemViewHolder) holder).applyBtn.setOnClickListener(view -> {
                loanAppInterface.onItemClicked(loanAppModelList.get(position), position);
            });

        } else if (holder.getItemViewType() == AD_VIEW) {
            ((AdViewHolder) holder).bindAdData();
        }
    }

    public int getItemViewType(int position) {
        if ((position + 1) % ITEM_FEED_COUNT == 0) {
            return AD_VIEW;
        }
        return ITEM_VIEW;
    }

    @Override
    public int getItemCount() {
        if (loanAppModelList.size() > 0) {
            return loanAppModelList.size() + Math.round(loanAppModelList.size() / ITEM_FEED_COUNT);
        }
        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateLoanAppList(List<LoanAppModel> loanAppModels) {
        loanAppModelList.clear();
        loanAppModelList.addAll(loanAppModels);
        Collections.shuffle(loanAppModelList);
        notifyDataSetChanged();
    }

    public interface LoanAppInterface {
        void onItemClicked(LoanAppModel loanAppModel, int position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView appName;
        ImageView appIcon;
        Button applyBtn;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            appName = itemView.findViewById(R.id.app_name);
            appIcon = itemView.findViewById(R.id.app_image);
            applyBtn = itemView.findViewById(R.id.apply_now_btn);

        }

    }

    public class AdViewHolder extends RecyclerView.ViewHolder {
        AdLayoutBinding binding;

        public AdViewHolder(@NonNull View itemAdView2) {
            super(itemAdView2);
            binding = AdLayoutBinding.bind(itemAdView2);
        }
        private void bindAdData() {
            if (Objects.equals(Paper.book().read(Prevalent.nativeAdsType), "Native")) {
                showAds.showNativeAds(context, binding.adLayout);
            } else if (Objects.equals(Paper.book().read(Prevalent.nativeAdsType), "MREC")) {
                showAds.showMrec(context, binding.adLayout);
            }
        }
    }


}
