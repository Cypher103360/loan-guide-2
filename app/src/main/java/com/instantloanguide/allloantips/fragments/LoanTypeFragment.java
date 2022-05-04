package com.instantloanguide.allloantips.fragments;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.instantloanguide.allloantips.activities.IncomeResourceActivity;
import com.instantloanguide.allloantips.databinding.FragmentLoanTypeBinding;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.CommonMethods;
import com.instantloanguide.allloantips.utils.ShowAds;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoanTypeFragment extends Fragment {

    FragmentLoanTypeBinding binding;
    ShowAds showAds;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoanTypeBinding.inflate(inflater, container, false);

         showAds = new ShowAds(requireActivity(), null, binding.adViewBottom);
        getLifecycle().addObserver(showAds);

        binding.loanOrPersonal.setOnClickListener(view -> {
            showAds.showInterstitialAds(requireActivity());
            Ads.destroyBanner();
            startActivity(new Intent(requireContext(), IncomeResourceActivity.class));
        });
        binding.anyOtherTypeLoan.setOnClickListener(view -> {
            showAds.showInterstitialAds(requireActivity());

            Ads.destroyBanner();
            startActivity(new Intent(requireContext(), IncomeResourceActivity.class));
        });

        binding.helpAndSupport.setOnClickListener(view -> {
            try {
                CommonMethods.whatsApp(requireContext());
            } catch (UnsupportedEncodingException | PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        });
        binding.emiCalculator.setOnClickListener(view -> {
            ArrayList<HashMap<String, Object>> items = new ArrayList<>();

            final PackageManager pm = requireActivity().getPackageManager();
            List<PackageInfo> packs = pm.getInstalledPackages(0);
            for (PackageInfo pi : packs) {
                if (pi.packageName.toLowerCase().contains("calcul")) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("appName", pi.applicationInfo.loadLabel(pm));
                    map.put("packageName", pi.packageName);
                    items.add(map);
                }
            }
            if (items.size() >= 1) {
                String packageName = (String) items.get(0).get("packageName");
                Intent i = pm.getLaunchIntentForPackage(packageName);
                if (i != null)
                    startActivity(i);
            } else {
                // Application not found
            }
        });

        return binding.getRoot();
    }
}