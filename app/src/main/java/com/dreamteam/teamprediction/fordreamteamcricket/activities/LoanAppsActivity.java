package com.dreamteam.teamprediction.fordreamteamcricket.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dreamteam.teamprediction.fordreamteamcricket.adapter.LoanAppsAdapter;
import com.dreamteam.teamprediction.databinding.ActivityLoanAppsBinding;
import com.dreamteam.teamprediction.fordreamteamcricket.models.ApiInterface;
import com.dreamteam.teamprediction.fordreamteamcricket.models.ApiWebServices;
import com.dreamteam.teamprediction.fordreamteamcricket.models.LoanAppModel;
import com.dreamteam.teamprediction.fordreamteamcricket.models.LoanAppModelList;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.CommonMethods;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.ShowAds;
import com.ironsource.mediationsdk.IronSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanAppsActivity extends AppCompatActivity implements LoanAppsAdapter.LoanAppInterface {

    ActivityLoanAppsBinding binding;
    RecyclerView loanAppsRecyclerView;
    LoanAppsAdapter loanAppsAdapter;
    ApiInterface apiInterface;
    String key, title;
    Dialog loading;
    List<LoanAppModel> loanAppModels = new ArrayList<>();
    ShowAds showAds = new ShowAds();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoanAppsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        key = getIntent().getStringExtra("key");
        title = getIntent().getStringExtra("title");
        binding.actTitle.setText(title);
        apiInterface = ApiWebServices.getApiInterface();
        loading = CommonMethods.getDialog(LoanAppsActivity.this);
        loading.show();
        fetchData(key);
        binding.refreshLayout.setOnRefreshListener(() -> {
            loading.show();
            fetchData(key);
            binding.refreshLayout.setRefreshing(false);
        });

        getLifecycle().addObserver(showAds);

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });

        loanAppsRecyclerView = binding.loanAppsRecyclerView;
        loanAppsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loanAppsRecyclerView.setHasFixedSize(true);
        loanAppsAdapter = new LoanAppsAdapter(LoanAppsActivity.this, this);
        loanAppsRecyclerView.setAdapter(loanAppsAdapter);
    }

    private void fetchData(String loanId) {
        Call<LoanAppModelList> call = apiInterface.fetchLoanAppDetails(loanId);
        call.enqueue(new Callback<LoanAppModelList>() {
            @Override
            public void onResponse(@NonNull Call<LoanAppModelList> call, @NonNull Response<LoanAppModelList> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getData() != null) {
                        loanAppModels.clear();
                        loanAppModels.addAll(response.body().getData());
                        loanAppsAdapter.updateLoanAppList(loanAppModels);
                        loading.dismiss();
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<LoanAppModelList> call, @NonNull Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        showAds.destroyBanner();
        super.onBackPressed();
        finish();
    }

    @Override
    public void onItemClicked(LoanAppModel loanAppModel, int position) {
        showAds.showInterstitialAds(this);
        showAds.destroyBanner();
        Intent intent = new Intent(LoanAppsActivity.this, AppDetailsActivity.class);
        intent.putExtra("img", loanAppModel.getImg());
        intent.putExtra("name", loanAppModel.getTitle());
        intent.putExtra("interest", loanAppModel.getInterest());
        intent.putExtra("amount", loanAppModel.getAmount());
        intent.putExtra("age", loanAppModel.getAge());
        intent.putExtra("requirement", loanAppModel.getRequirement());
        intent.putExtra("url", loanAppModel.getUrl());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showAds.showTopBanner(this, binding.adViewTop);
        showAds.showBottomBanner(this, binding.adViewBottom);

    }

    @Override
    protected void onResume() {
        super.onResume();
        IronSource.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this);
    }
}