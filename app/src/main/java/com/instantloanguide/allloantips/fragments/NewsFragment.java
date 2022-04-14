package com.instantloanguide.allloantips.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {
    FragmentNewsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater,container,false);

        binding.newsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return binding.getRoot();
    }
}