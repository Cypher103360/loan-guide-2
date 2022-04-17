package com.instantloanguide.allloantips.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.AdLayoutBinding;
import com.instantloanguide.allloantips.models.NewsClickInterface;
import com.instantloanguide.allloantips.models.NewsModel;

import java.util.ArrayList;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private static final int ITEM_VIEW = 0;
    private static final int AD_VIEW = 1;
    private static final int ITEM_FEED_COUNT = 5;
    List<NewsModel> newsModels = new ArrayList<>();
    Context context;
    NewsClickInterface newsClickInterface;

    public NewsAdapter(Context context, NewsClickInterface newsClickIterface) {
        this.context = context;
        this.newsClickInterface = newsClickIterface;
    }

    @Override
    public int getItemViewType(int position) {
        if ((position + 1) % ITEM_FEED_COUNT == 0) {
            return AD_VIEW;
        }
        return ITEM_VIEW;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW){
            View view = LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false);
            return new ViewHolder(view);
        }else if (viewType == AD_VIEW){
            View view = LayoutInflater.from(context).inflate(R.layout.ad_layout,parent,false);
            return new AdViewHolder(view);
        }
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(newsModels.get(position).getNewsTitle());
        Glide.with(context).load("https://gedgetsworld.in/Loan_App/news_images/" + newsModels.get(position).getNewsImg()).into(holder.newsImg);
        holder.itemView.setOnClickListener(view -> newsClickInterface.onClicked(newsModels.get(position)));

    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public void updateList(List<NewsModel> newsModelList) {
        newsModels.clear();
        newsModels.addAll(newsModelList);
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

    public static class AdViewHolder extends RecyclerView.ViewHolder{
        AdLayoutBinding binding;

        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AdLayoutBinding.bind(itemView);
        }

        private void bindAdData(){
            AdLoader.Builder builder = new
        }
    }
}