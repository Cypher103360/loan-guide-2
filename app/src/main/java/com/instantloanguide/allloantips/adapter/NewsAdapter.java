package com.instantloanguide.allloantips.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.activities.AppDetailsActivity;
import com.instantloanguide.allloantips.databinding.AdLayoutBinding;
import com.instantloanguide.allloantips.models.LoanAppModel;
import com.instantloanguide.allloantips.models.NewsClickInterface;
import com.instantloanguide.allloantips.models.NewsModel;
import com.instantloanguide.allloantips.utils.Prevalent;
import com.instantloanguide.allloantips.utils.ShowAds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;


public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<NewsModel> newsModels = new ArrayList<>();
    Activity context;
    NewsClickInterface newsClickInterface;
    private static final int ITEM_VIEW = 0;
    private static final int AD_VIEW = 1;
    private static final int ITEM_FEED_COUNT = 3;
    ShowAds showAds = new ShowAds();

    public NewsAdapter(Activity context, NewsClickInterface newsClickIterface) {
        this.context = context;
        this.newsClickInterface = newsClickIterface;
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
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false));

        } else if (viewType == AD_VIEW) {
            View view = LayoutInflater.from(context).inflate(R.layout.ad_layout, parent, false);
            return new AdViewHolder(view);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {

        if (holder.getItemViewType() == ITEM_VIEW) {
            int position = pos - Math.round(pos / ITEM_FEED_COUNT);
            ((ViewHolder)holder).title.setText(newsModels.get(position).getNewsTitle());
            Glide.with(context).load("https://gedgetsworld.in/Loan_App/news_images/" + newsModels.get(position).getNewsImg()).into(((ViewHolder)holder).newsImg);
            holder.itemView.setOnClickListener(view -> newsClickInterface.onClicked(newsModels.get(position)));


        } else if (holder.getItemViewType() == AD_VIEW) {
            ((AdViewHolder) holder).bindAdData();
        }


    }

    @Override
    public int getItemCount() {

        if (newsModels.size() > 0) {
            return newsModels.size() + Math.round(newsModels.size() / ITEM_FEED_COUNT);
        }
        return 0;
    }

    public void updateList(List<NewsModel> newsModelList) {
        newsModels.clear();
        newsModels.addAll(newsModelList);
        Collections.reverse(newsModels);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView newsImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            newsImg = itemView.findViewById(R.id.news_icon);
        }
    }
    public  class AdViewHolder extends RecyclerView.ViewHolder {
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