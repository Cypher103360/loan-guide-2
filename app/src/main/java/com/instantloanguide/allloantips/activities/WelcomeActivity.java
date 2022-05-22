package com.instantloanguide.allloantips.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.databinding.ActivityWelcomeBinding;
import com.instantloanguide.allloantips.models.ApiInterface;
import com.instantloanguide.allloantips.models.ApiWebServices;
import com.instantloanguide.allloantips.models.MessageModel;
import com.instantloanguide.allloantips.utils.Ads;
import com.instantloanguide.allloantips.utils.CommonMethods;
import com.instantloanguide.allloantips.utils.ShowAds;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;
    MaterialAlertDialogBuilder builder;
    ApiInterface apiInterface;
  //  Map<String,String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        AppLovinSdk.getInstance( this ).showMediationDebugger();
        ShowAds showAds = new ShowAds(this, binding.adViewTop, binding.adViewBottom);
        getLifecycle().addObserver(showAds);
        apiInterface = ApiWebServices.getApiInterface();

        binding.startBtn.setOnClickListener(view -> {
            binding.lottieRunning.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                Ads.destroyBanner();
                showAds.showInterstitialAds(this);
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                binding.lottieRunning.setVisibility(View.GONE);
            }, 500);
        });

        binding.contactBtn.setOnClickListener(view -> {
            try {
                CommonMethods.whatsApp(this);
            } catch (UnsupportedEncodingException | PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        });
        binding.shareBtn.setOnClickListener(view -> CommonMethods.shareApp(this));

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account != null) {
//            String name = account.getDisplayName();
//            String email = account.getEmail();
//            map.put("name", name);
//            map.put("email", email);
//            uploadUserData(map);
//        }
    }

//    private void uploadUserData(Map<String, String> map) {
//        Call<MessageModel> call = apiInterface.uploadUserData(map);
//        call.enqueue(new Callback<MessageModel>() {
//            @Override
//            public void onResponse(@NonNull Call<MessageModel> call, @NonNull Response<MessageModel> response) {
//                if (response.isSuccessful()) {
//                    assert response.body() != null;
//                   // Toast.makeText(WelcomeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<MessageModel> call, @NonNull Throwable t) {
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        builder =
                new MaterialAlertDialogBuilder(this);
        builder.setTitle(R.string.app_name)
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Do You Really Want To Exit?\nAlso Rate Us 5 Star.")
                .setNeutralButton("CANCEL", (dialog, which) -> {
                });


        builder.setNegativeButton("RATE APP", (dialog, which) -> CommonMethods.rateApp(getApplicationContext()))
                .setPositiveButton("OK!!", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    moveTaskToBack(true);
                    System.exit(0);

                });
        builder.show();
    }

}