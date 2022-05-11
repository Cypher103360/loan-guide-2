package com.instantloanguide.loanguideadmin.adapters;

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
import com.instantloanguide.loanguideadmin.R;
import com.instantloanguide.loanguideadmin.models.LoanAppModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LoanAppsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<LoanAppModel> loanAppModelList = new ArrayList<>();
    Activity context;
    LoanAppInterface loanAppInterface;


    public LoanAppsAdapter(Activity context, LoanAppInterface loanAppInterface) {
        this.context = context;
        this.loanAppInterface = loanAppInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item_layout, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).appName.setText(loanAppModelList.get(position).getTitle());
        Glide.with(context).load("https://gedgetsworld.in/Loan_App/loan_app_images/" + loanAppModelList.get(position).getImg()).into(((ItemViewHolder) holder).appIcon);

        holder.itemView.setOnClickListener(view -> loanAppInterface.onItemClicked(loanAppModelList.get(position), position));
    }


    @Override
    public int getItemCount() {

        return loanAppModelList.size();
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


}
