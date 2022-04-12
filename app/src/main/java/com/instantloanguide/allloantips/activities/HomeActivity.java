package com.instantloanguide.allloantips.activities;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.instantloanguide.allloantips.activities.ui.main.SectionsPagerAdapter;
import com.instantloanguide.allloantips.databinding.ActivityHomeBinding;
import com.instantloanguide.allloantips.fragments.LoanTypeFragment;
import com.instantloanguide.allloantips.fragments.NewsFragment;
import com.instantloanguide.allloantips.fragments.TipsFragment;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        sectionsPagerAdapter.addFragment(new LoanTypeFragment(),"Loan Types");
        sectionsPagerAdapter.addFragment(new NewsFragment(),"News");
        sectionsPagerAdapter.addFragment(new TipsFragment(),"Tips");
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
}