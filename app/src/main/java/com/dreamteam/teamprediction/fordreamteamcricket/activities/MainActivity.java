package com.dreamteam.teamprediction.fordreamteamcricket.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.dreamteam.teamprediction.R;
import com.dreamteam.teamprediction.databinding.ActivityMainBinding;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.MyReceiver;

public class MainActivity extends AppCompatActivity {
    public static final String BroadCastStringForAction = "checkingInternet";
    FirebaseAnalytics firebaseAnalytics;
    int REQUEST_CODE = 11;
    ActivityMainBinding binding;
    int count = 1;
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BroadCastStringForAction)) {
                if (intent.getStringExtra("online_status").equals("true")) {

                    Set_Visibility_ON();
                    count++;
                } else {
                    Set_Visibility_OFF();
                }
            }
        }
    };
    private IntentFilter intentFilter;

    private void Set_Visibility_OFF() {
        binding.loadingLottie.setVisibility(View.GONE);
        binding.logoImage.setVisibility(View.GONE);
        binding.tvNotConnected.setVisibility(View.VISIBLE);
        binding.noInternetLottie.setVisibility(View.VISIBLE);
        binding.splashContainer.setBackgroundColor(0);
    }

    private void Set_Visibility_ON() {
        binding.logoImage.setVisibility(View.VISIBLE);
        binding.loadingLottie.setVisibility(View.VISIBLE);
        binding.tvNotConnected.setVisibility(View.GONE);
        binding.noInternetLottie.setVisibility(View.GONE);
        binding.splashContainer.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500));


        if (count == 2) {
            new Handler().postDelayed(() -> {
//                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
//                if (account != null) {
//                    startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
//                } else {
//                    startActivity(new Intent(MainActivity.this, SignUpActivity.class));
//                }
                finish();
            }, 100);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        inAppUpdate();

        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);
        Intent serviceIntent = new Intent(this, MyReceiver.class);
        startService(serviceIntent);
        binding.tvNotConnected.setVisibility(View.GONE);
        if (isOnline(getApplicationContext())) {
            Set_Visibility_ON();
        } else {
            Set_Visibility_OFF();
        }


    }

    public boolean isOnline(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void inAppUpdate() {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.IMMEDIATE, MainActivity.this, REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            Toast.makeText(MainActivity.this, "Downloading Started", Toast.LENGTH_SHORT).show();
            if (resultCode != RESULT_OK) {
                Log.d("DDDDD", "Downloading Failed" + resultCode);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}