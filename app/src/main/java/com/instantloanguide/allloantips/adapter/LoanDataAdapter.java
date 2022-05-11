package com.instantloanguide.allloantips.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instantloanguide.allloantips.BuildConfig;
import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.models.LoanModel;
import com.instantloanguide.allloantips.models.LoanModelList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoanDataAdapter extends RecyclerView.Adapter<LoanDataAdapter.ViewHolder> {
    List<LoanModel> loanModelList = new ArrayList<>();
    Activity context;

    public LoanDataAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.codes_layout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.userName.setText(loanModelList.get(position).getUserName());
        holder.loanName.setText(loanModelList.get(position).getLoanName());
        holder.loanAmount.setText(" ₹ " + loanModelList.get(position).getLoanAmount());
        Glide.with(context).load("https://gedgetsworld.in/Loan_App/status_images/"
                + loanModelList.get(position).getImages()).into(holder.profileImg);
        holder.showFullProfileImage(loanModelList.get(position).getImages());

        holder.profileImg.setOnClickListener(v -> {
          //  CommonMethod.interstitialAds(context);
            holder.dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return loanModelList.size();
    }

    public void updateLoanModelList(List<LoanModel> loanModels) {
        loanModelList.clear();
        loanModelList.addAll(loanModels);
        Collections.reverse(loanModelList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName, loanName, loanAmount;
        Button shareBtn;
        CircleImageView profileImg;
        Dialog dialog;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name_tv);
            loanName = itemView.findViewById(R.id.loan_name_tv);
            loanAmount = itemView.findViewById(R.id.loan_amount_tv);
            profileImg = itemView.findViewById(R.id.profileImg);
            shareBtn = itemView.findViewById(R.id.share_code_btn);
            shareBtn.setOnClickListener(v -> shareApp());

        }

        private void shareApp() {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                itemView.getContext().startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void showFullProfileImage(String images) {
            dialog = new Dialog(itemView.getContext());
            dialog.setContentView(R.layout.show_full_image);
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.item_bg));
            dialog.setCancelable(true);
            ImageView imageView = dialog.findViewById(R.id.fullImage);
            Glide.with(itemView.getContext()).load("https://gedgetsworld.in/Loan_App/status_images/" + images).into(imageView);
        }

    }
}