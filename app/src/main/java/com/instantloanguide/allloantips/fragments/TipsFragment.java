package com.instantloanguide.allloantips.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.activities.TipsDetailActivity;
import com.instantloanguide.allloantips.adapter.TipsAdapter;
import com.instantloanguide.allloantips.databinding.FragmentTipsBinding;
import com.instantloanguide.allloantips.models.TipsClickInterface;
import com.instantloanguide.allloantips.models.TipsModel;
import com.instantloanguide.allloantips.models.TipsModelList;
import com.instantloanguide.allloantips.models.TipsViewModel;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.CommonMethods;
import com.instantloanguide.allloantips.utils.ShowAds;

public class TipsFragment extends Fragment implements TipsClickInterface {
    FragmentTipsBinding binding;
    TipsAdapter tipsAdapter;
    RecyclerView tipsRecyclerView;
    Dialog loadingDialog;
    TipsViewModel tipsViewModel;
    FirebaseAnalytics firebaseAnalytics;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTipsBinding.inflate(getLayoutInflater());
        tipsRecyclerView = binding.tipsRecyclerView;
        tipsViewModel = new ViewModelProvider(this).get(TipsViewModel.class);
        tipsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        tipsRecyclerView.setHasFixedSize(true);
        loadingDialog = CommonMethods.getDialog(requireContext());
        loadingDialog.show();
        setTipsData(requireActivity());
        ShowAds showAds = new ShowAds(requireActivity(), null, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            setTipsData(requireActivity());
            binding.swipeRefresh.setRefreshing(false);
        });

        return binding.getRoot();
    }

    private void setTipsData(FragmentActivity requireActivity) {
        tipsAdapter = new TipsAdapter(requireActivity,this);
        tipsRecyclerView.setAdapter(tipsAdapter);
        tipsViewModel.getAllTips().observe(requireActivity, tipsModelList -> {
            if (!tipsModelList.getData().isEmpty()){
                tipsAdapter.updateList(tipsModelList.getData());
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    public void onClicked(TipsModel tipsModel) {
        Ads.destroyBanner();
        Intent intent = new Intent(requireActivity(), TipsDetailActivity.class);
        intent.putExtra("id",tipsModel.getId());
        intent.putExtra("title",tipsModel.getTipsTitle());
        intent.putExtra("engDesc",tipsModel.getTipsEngDesc());
        intent.putExtra("hinDesc",tipsModel.getTipsHinDesc());
        startActivity(intent);

        firebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, tipsModel.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, tipsModel.getTipsTitle());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "TIPS LIST");
        firebaseAnalytics.logEvent("Clicked_Tips_Items", bundle);

    }
}