package com.dreamteam.teamprediction.fordreamteamcricket.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.dreamteam.teamprediction.fordreamteamcricket.activities.NewsDetailsActivity;
import com.dreamteam.teamprediction.fordreamteamcricket.adapter.NewsAdapter;
import com.dreamteam.teamprediction.databinding.FragmentNewsBinding;
import com.dreamteam.teamprediction.fordreamteamcricket.models.ApiInterface;
import com.dreamteam.teamprediction.fordreamteamcricket.models.ApiWebServices;
import com.dreamteam.teamprediction.fordreamteamcricket.models.BannerModel;
import com.dreamteam.teamprediction.fordreamteamcricket.models.BannerModelList;
import com.dreamteam.teamprediction.fordreamteamcricket.models.NewsClickInterface;
import com.dreamteam.teamprediction.fordreamteamcricket.models.NewsModel;
import com.dreamteam.teamprediction.fordreamteamcricket.models.NewsViewModel;
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

public class NewsFragment extends Fragment implements NewsClickInterface {
    FragmentNewsBinding binding;
    NewsAdapter newsAdapter;
    RecyclerView newsRecyclerView;
    String banUrl;
    Dialog loadingDialog;
    Map<String,String> map = new HashMap<>();
    NewsViewModel newsViewModel;
    FirebaseAnalytics firebaseAnalytics;
    ApiInterface apiInterface;

    ShowAds showAds = new ShowAds();
    List<NewsModel> newsModels;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        apiInterface = ApiWebServices.getApiInterface();
        map.put("title", "news");
        fetchBannerImages();
        newsRecyclerView = binding.newsRecyclerView;
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        newsRecyclerView.setHasFixedSize(true);
        loadingDialog = CommonMethods.getDialog(requireContext());
        loadingDialog.show();
        setNewsData(requireActivity());

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
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setNewsData(requireActivity());
                binding.swipeRefresh.setRefreshing(false);
            }
        });

        return binding.getRoot();
    }

    public void setNewsData(Activity context) {
        newsModels= new ArrayList<>();
        newsAdapter = new NewsAdapter(context,this);
        newsRecyclerView.setAdapter(newsAdapter);
        newsViewModel.getAllNews().observe(requireActivity(), newsModelList -> {
            if (!newsModelList.getData().isEmpty()){
                newsModels.clear();
                newsModels.addAll(newsModelList.getData());
                newsAdapter.updateList(newsModels);
                loadingDialog.dismiss();
            }
        });

    }

    public void fetchBannerImages(){
        Call<BannerModelList> call = apiInterface.fetchBanner(map);
        call.enqueue(new Callback<BannerModelList>() {
            @Override
            public void onResponse(@NonNull Call<BannerModelList> call, @NonNull Response<BannerModelList> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getData() != null) {

                        for (BannerModel ban : response.body().getData()) {
                            Glide.with(requireActivity()).load("https://gedgetsworld.in/Loan_App/strip_banner_images/"
                                    + ban.getImage()).into(binding.newsBannerImageView);
                            banUrl = ban.getUrl();
                            loadingDialog.dismiss();
                        }
                        binding.newsBannerImageView.setOnClickListener(v -> {
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
    public void onClicked(NewsModel newsModel) {

        showAds.showInterstitialAds(requireActivity());
        showAds.destroyBanner();
        Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
        intent.putExtra("id",newsModel.getId());
        intent.putExtra("image",newsModel.getNewsImg());
        intent.putExtra("title",newsModel.getNewsTitle());
        intent.putExtra("engTitle",newsModel.getNewsEngTitle());
        intent.putExtra("url",newsModel.getUrl());
        intent.putExtra("engDesc",newsModel.getNewsEngDesc());
        intent.putExtra("hinDesc",newsModel.getNewsHinDesc());
        startActivity(intent);

        firebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, newsModel.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, newsModel.getNewsTitle());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "NEWS LIST");
        firebaseAnalytics.logEvent("Clicked_News_Items", bundle);

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