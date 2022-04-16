package com.instantloanguide.allloantips.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.instantloanguide.allloantips.activities.NewsDetailsActivity;
import com.instantloanguide.allloantips.activities.ui.main.PageViewModel;
import com.instantloanguide.allloantips.adapter.NewsAdapter;
import com.instantloanguide.allloantips.databinding.FragmentNewsBinding;
import com.instantloanguide.allloantips.models.NewsClickInterface;
import com.instantloanguide.allloantips.models.NewsModel;
import com.instantloanguide.allloantips.models.NewsModelList;
import com.instantloanguide.allloantips.models.NewsViewModel;
import com.instantloanguide.allloantips.utils.CommonMethods;

public class NewsFragment extends Fragment implements NewsClickInterface {
    FragmentNewsBinding binding;
    NewsAdapter newsAdapter;
    RecyclerView newsRecyclerView;
    Dialog loadingDialog;
    NewsViewModel newsViewModel;
    FirebaseAnalytics firebaseAnalytics;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        newsRecyclerView = binding.newsRecyclerView;
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        newsRecyclerView.setHasFixedSize(true);
        loadingDialog = CommonMethods.getDialog(requireContext());
        loadingDialog.show();
        setNewsData(requireActivity());
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
        newsAdapter = new NewsAdapter(context,this);
        newsRecyclerView.setAdapter(newsAdapter);
        newsViewModel.getAllNews().observe(requireActivity(), newsModelList -> {
            if (!newsModelList.getData().isEmpty()){
                newsAdapter.updateList(newsModelList.getData());
                loadingDialog.dismiss();
            }
        });

    }

    @Override
    public void onClicked(NewsModel newsModel) {

        Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
        intent.putExtra("id",newsModel.getId());
        intent.putExtra("image",newsModel.getNewsImg());
        intent.putExtra("title",newsModel.getNewsTitle());
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
}