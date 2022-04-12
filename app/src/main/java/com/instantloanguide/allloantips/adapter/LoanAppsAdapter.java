package com.instantloanguide.allloantips.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.activities.AppDetailsActivity;
import com.instantloanguide.allloantips.models.LoanAppModel;

import java.util.ArrayList;
import java.util.List;

public class LoanAppsAdapter extends RecyclerView.Adapter<LoanAppsAdapter.ViewHolder> {

    List<LoanAppModel> loanAppModelList = new ArrayList<>();
    Context context;
    LoanAppInterface loanAppInterface;

    public LoanAppsAdapter(Context context, LoanAppInterface loanAppInterface) {
        this.context = context;
        this.loanAppInterface = loanAppInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.appName.setText(loanAppModelList.get(position).getAppName());
        holder.appIcon.setImageResource(R.drawable.ic_launcher_foreground);
        holder.applyBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, AppDetailsActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return loanAppModelList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateLoanAppList(List<LoanAppModel> loanAppModels) {
        loanAppModelList.clear();
        loanAppModelList.addAll(loanAppModels);
        notifyDataSetChanged();
    }

    public interface LoanAppInterface {
        void onItemClicked(LoanAppModel loanAppModel, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView appName;
        ImageView appIcon;
        Button applyBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appName = itemView.findViewById(R.id.app_name);
            appIcon = itemView.findViewById(R.id.app_image);
            applyBtn = itemView.findViewById(R.id.apply_now_btn);
        }
    }
}
