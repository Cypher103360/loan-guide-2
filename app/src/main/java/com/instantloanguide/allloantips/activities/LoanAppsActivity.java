package com.instantloanguide.allloantips.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.adapter.LoanAppsAdapter;
import com.instantloanguide.allloantips.databinding.ActivityLoanAppsBinding;
import com.instantloanguide.allloantips.models.LoanAppModel;

import java.util.ArrayList;
import java.util.List;

public class LoanAppsActivity extends AppCompatActivity implements LoanAppsAdapter.LoanAppInterface {

    ActivityLoanAppsBinding binding;
    RecyclerView loanAppsRecyclerView;
    List<LoanAppModel> loanAppModelList = new ArrayList<>();
    LoanAppsAdapter loanAppsAdapter;
    String id,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoanAppsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        binding.actTitle.setText(title);

        loanAppsRecyclerView = binding.loanAppsRecyclerView;
        loanAppsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loanAppsRecyclerView.setHasFixedSize(true);
        loanAppsAdapter = new LoanAppsAdapter(LoanAppsActivity.this,this);
        loanAppsRecyclerView.setAdapter(loanAppsAdapter);

        if (id.equals("personalLoan")) {
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller1"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller2"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller3"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller4"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller5"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller6"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller7"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller8"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller9"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "True Caller"));
        }else if (id.equals("bankLoan")){
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank1"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank2"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank3"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank4"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank5"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank6"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank7"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank8"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank9"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "HDFC Bank"));

        }else if (id.equals("businessLoan")){
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Shoe Business1"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Shoe Business2"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Shoe Business3"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Shoe Business4"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Shoe Business5"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Shoe Business6"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Shoe Business7"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Shoe Business8"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Shoe Business9"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Shoe Business"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Shoe Business"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Shoe Business"));


        }else if (id.equals("homeLoan")){
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks1"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks2"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks3"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks4"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks5"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks6"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks7"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks8"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks9"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Home Bricks"));

        }else if (id.equals("aadharLoan")){
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Aadhar Loan"));

        }else if (id.equals("studentLoan")){
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
            loanAppModelList.add(new LoanAppModel("1", R.drawable.ic_launcher_foreground, "Student Loan"));
        }

        loanAppsAdapter.updateLoanAppList(loanAppModelList);

    }

    @Override
    public void onItemClicked(LoanAppModel loanAppModel, int position) {

    }
}