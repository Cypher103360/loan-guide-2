package com.instantloanguide.loanguideadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instantloanguide.loanguideadmin.R;
import com.instantloanguide.loanguideadmin.models.NewsModel;

import java.util.ArrayList;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    List<NewsModel> newsModels = new ArrayList<>();
    Context context;
    NewsClickInterface newsClickInterface;

    public NewsAdapter(Context context, NewsClickInterface newsClickIterface) {
        this.context = context;
        this.newsClickInterface = newsClickIterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(newsModels.get(position).getNewsTitle());
        Glide.with(context).load("https://gedgetsworld.in/Loan_App/news_images/" + newsModels.get(position).getNewsImg()).into(holder.newsImg);
        holder.itemView.setOnClickListener(view -> newsClickInterface.onclicked(newsModels.get(position)));

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
            title = itemView.findViewById(R.id.newsTitle);
            newsImg = itemView.findViewById(R.id.newsImage);
        }
    }


}