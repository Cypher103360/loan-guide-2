package com.instantloanguide.allloantips.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.activities.IncomeResourceActivity;
import com.instantloanguide.allloantips.databinding.FragmentLoanTypeBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.ShowAds;

public class LoanTypeFragment extends Fragment {

    FragmentLoanTypeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoanTypeBinding.inflate(inflater,container,false);

        ShowAds showAds = new ShowAds(requireActivity(), null, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        binding.loanOrPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads.destroyBanner();
                startActivity(new Intent(requireContext(), IncomeResourceActivity.class));
            }
        });
        binding.anyOtherTypeLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads.destroyBanner();
                startActivity(new Intent(requireContext(), IncomeResourceActivity.class));
            }
        });


        return binding.getRoot();
    }
}