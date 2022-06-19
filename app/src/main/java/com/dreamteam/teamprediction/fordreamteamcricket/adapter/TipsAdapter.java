package com.dreamteam.teamprediction.fordreamteamcricket.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dreamteam.teamprediction.R;
import com.dreamteam.teamprediction.databinding.AdLayoutBinding;
import com.dreamteam.teamprediction.fordreamteamcricket.models.TipsClickInterface;
import com.dreamteam.teamprediction.fordreamteamcricket.models.TipsModel;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.Prevalent;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.ShowAds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;


public class TipsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW = 0;
    private static final int AD_VIEW = 1;
    private static final int ITEM_FEED_COUNT = 3;
    List<TipsModel> tipsModels = new ArrayList<>();
    Activity context;
    TipsClickInterface clickInterface;
    ShowAds showAds = new ShowAds();


    public TipsAdapter(Activity context, TipsClickInterface clickInterface) {
        this.context = context;
        this.clickInterface = clickInterface;
    }

    public int getItemViewType(int position) {
        if ((position + 1) % ITEM_FEED_COUNT == 0) {
            return AD_VIEW;
        }
        return ITEM_VIEW;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.tips_layout, parent, false));


        } else if (viewType == AD_VIEW) {
            View view = LayoutInflater.from(context).inflate(R.layout.ad_layout, parent, false);
            return new AdViewHolder(view);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        if (holder.getItemViewType() == ITEM_VIEW) {

            int position = pos - Math.round(pos / ITEM_FEED_COUNT);
            ((ViewHolder) holder).title.setText(tipsModels.get(position).getTipsTitle());
            holder.itemView.setOnClickListener(view -> clickInterface.onClicked(tipsModels.get(position)));


        } else if (holder.getItemViewType() == AD_VIEW) {
            ((AdViewHolder) holder).bindAdData();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<TipsModel> tipsModelList) {
        tipsModels.clear();
        tipsModels.addAll(tipsModelList);
        Collections.reverse(tipsModels);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (tipsModels.size() > 0) {
            return tipsModels.size() + Math.round(tipsModels.size() / ITEM_FEED_COUNT);
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tipsTitle);

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