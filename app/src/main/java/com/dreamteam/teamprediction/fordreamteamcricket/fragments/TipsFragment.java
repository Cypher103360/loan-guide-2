package com.dreamteam.teamprediction.fordreamteamcricket.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.dreamteam.teamprediction.fordreamteamcricket.activities.TipsDetailActivity;
import com.dreamteam.teamprediction.fordreamteamcricket.adapter.TipsAdapter;
import com.dreamteam.teamprediction.databinding.FragmentTipsBinding;
import com.dreamteam.teamprediction.fordreamteamcricket.models.ApiInterface;
import com.dreamteam.teamprediction.fordreamteamcricket.models.ApiWebServices;
import com.dreamteam.teamprediction.fordreamteamcricket.models.BannerModel;
import com.dreamteam.teamprediction.fordreamteamcricket.models.BannerModelList;
import com.dreamteam.teamprediction.fordreamteamcricket.models.TipsClickInterface;
import com.dreamteam.teamprediction.fordreamteamcricket.models.TipsModel;
import com.dreamteam.teamprediction.fordreamteamcricket.models.TipsViewModel;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.CommonMethods;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.Prevalent;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.ShowAds;
import com.ironsource.mediationsdk.IronSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipsFragment extends Fragment implements TipsClickInterface {
    FragmentTipsBinding binding;
    TipsAdapter tipsAdapter;
    RecyclerView tipsRecyclerView;
    Dialog loadingDialog;
    TipsViewModel tipsViewModel;
    FirebaseAnalytics firebaseAnalytics;
    ShowAds showAds = new ShowAds();
    String banUrl;
    Map<String, String> map = new HashMap<>();
    ApiInterface apiInterface;
    List<TipsModel> tipsModels;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTipsBinding.inflate(getLayoutInflater());
        apiInterface = ApiWebServices.getApiInterface();
        map.put("title", "tips");
        fetchBannerImages();
        tipsRecyclerView = binding.tipsRecyclerView;
        tipsViewModel = new ViewModelProvider(this).get(TipsViewModel.class);
        tipsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        tipsRecyclerView.setHasFixedSize(true);
        loadingDialog = CommonMethods.getDialog(requireContext());
        loadingDialog.show();
        setTipsData(requireActivity());
        if (Paper.book().read(Prevalent.bannerTopNetworkName).equals("IronSourceWithMeta")) {
            binding.adViewTop.setVisibility(View.GONE);
            showAds.showBottomBanner(requireActivity(), binding.adViewBottom);

        } else if (Paper.book().read(Prevalent.bannerBottomNetworkName).equals("IronSourceWithMeta")) {
            binding.adViewBottom.setVisibility(View.GONE);
            showAds.showTopBanner(requireActivity(), binding.adViewTop);

        } else {
            showAds.showTopBanner(requireActivity(), binding.adViewTop);
            showAds.showBottomBanner(requireActivity(), binding.adViewBottom);
        }

        binding.swipeRefresh.setOnRefreshListener(() -> {
            setTipsData(requireActivity());
            binding.swipeRefresh.setRefreshing(false);
        });

        return binding.getRoot();
    }

    private void setTipsData(FragmentActivity requireActivity) {
        tipsModels = new ArrayList<>();
        tipsAdapter = new TipsAdapter(requireActivity, this);
        tipsRecyclerView.setAdapter(tipsAdapter);
        tipsViewModel.getAllTips().observe(requireActivity, tipsModelList -> {
            if (!tipsModelList.getData().isEmpty()) {
                tipsModels.clear();
                tipsModels.addAll(tipsModelList.getData());
                tipsAdapter.updateList(tipsModels);
                loadingDialog.dismiss();
            }
        });
    }

    public void fetchBannerImages() {
        Call<BannerModelList> call = apiInterface.fetchBanner(map);
        call.enqueue(new Callback<BannerModelList>() {
            @Override
            public void onResponse(@NonNull Call<BannerModelList> call, @NonNull Response<BannerModelList> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getData() != null) {

                        for (BannerModel ban : response.body().getData()) {
                            Glide.with(requireActivity()).load("https://gedgetsworld.in/Loan_App/strip_banner_images/"
                                    + ban.getImage()).into(binding.tipsBannerImageView);
                            banUrl = ban.getUrl();
                            loadingDialog.dismiss();
                        }
                        binding.tipsBannerImageView.setOnClickListener(v -> {
                            openWebPage(banUrl);
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BannerModelList> call, @NonNull Throwable t) {

            }
        });
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onClicked(TipsModel tipsModel) {
        showAds.destroyBanner();
        showAds.showInterstitialAds(requireActivity());

        Intent intent = new Intent(requireActivity(), TipsDetailActivity.class);
        intent.putExtra("id", tipsModel.getId());
        intent.putExtra("title", tipsModel.getTipsTitle());
        intent.putExtra("engTitle", tipsModel.getTipsEngTitle());
        intent.putExtra("url", tipsModel.getTipsUrl());
        intent.putExtra("engDesc", tipsModel.getTipsEngDesc());
        intent.putExtra("hinDesc", tipsModel.getTipsHinDesc());
        startActivity(intent);

        firebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, tipsModel.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, tipsModel.getTipsTitle());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "TIPS LIST");
        firebaseAnalytics.logEvent("Clicked_Tips_Items", bundle);

    }

    @Override
    public void onResume() {
        super.onResume();
        IronSource.onResume(requireActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        IronSource.onPause(requireActivity());
    }
}