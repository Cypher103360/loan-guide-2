package com.instantloanguide.allloantips.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.models.TipsClickInterface;
import com.instantloanguide.allloantips.models.TipsModel;

import java.util.ArrayList;
import java.util.List;


public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.ViewHolder> {

    List<TipsModel> tipsModels = new ArrayList<>();
    Context context;
    TipsClickInterface clickInterface;

    public TipsAdapter(Context context, TipsClickInterface clickInterface) {
        this.context = context;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.tips_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(tipsModels.get(position).getTipsTitle());
        holder.itemView.setOnClickListener(view -> clickInterface.onClicked(tipsModels.get(position)));

    }

    public void updateList(List<TipsModel> tipsModelList) {
        tipsModels.clear();
        tipsModels.addAll(tipsModelList);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return tipsModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tipsTitle);

        }
    }
}