package com.dreamteam.teamprediction.fordreamteamcricket.activities;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static com.dreamteam.teamprediction.fordreamteamcricket.fragments.StatusFragment.setImage;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.dreamteam.teamprediction.R;
import com.dreamteam.teamprediction.fordreamteamcricket.activities.ui.main.SectionsPagerAdapter;
import com.dreamteam.teamprediction.databinding.ActivityHomeBinding;
import com.dreamteam.teamprediction.fordreamteamcricket.fragments.LoanTypeFragment;
import com.dreamteam.teamprediction.fordreamteamcricket.fragments.NewsFragment;
import com.dreamteam.teamprediction.fordreamteamcricket.fragments.StatusFragment;
import com.dreamteam.teamprediction.fordreamteamcricket.fragments.TipsFragment;
import com.dreamteam.teamprediction.fordreamteamcricket.models.ApiInterface;
import com.dreamteam.teamprediction.fordreamteamcricket.models.ApiWebServices;
import com.dreamteam.teamprediction.fordreamteamcricket.models.UrlModel;
import com.dreamteam.teamprediction.fordreamteamcricket.models.UrlModelList;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.CommonMethods;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.MyReceiver;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.Prevalent;
import com.dreamteam.teamprediction.fordreamteamcricket.utils.ShowAds;
import com.ironsource.mediationsdk.IronSource;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String BroadCastStringForAction = "checkingInternet";
    private static final float END_SCALE = 0.7f;
    public static Uri uri;
    Dialog loading;
    //    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;
    FirebaseAnalytics mFirebaseAnalytics;
    Bundle bundle;
    int count = 1;
    String tipsUrl;
    ApiInterface apiInterface;
    Map<String, String> map = new HashMap<>();
    ImageView navMenu, headerImage;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ConstraintLayout categoryContainer;
    IntentFilter intentFilter;
    SectionsPagerAdapter sectionsPagerAdapter;
    ShowAds showAds = new ShowAds();
    private ActivityHomeBinding binding;
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.M)
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

    private void Set_Visibility_ON() {
        binding.lottieHome.setVisibility(View.GONE);
        binding.tvNotConnected.setVisibility(View.GONE);
        binding.lottieContact.setVisibility(View.VISIBLE);
        binding.viewPager.setVisibility(View.VISIBLE);
        binding.tabs.setVisibility(View.VISIBLE);
        enableNavItems();
        map.put("title", "finance_tips");
        fetchTipsUrl(map);
        if (count == 2) {
            ViewPager viewPager = binding.viewPager;
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = binding.tabs;
            tabs.setupWithViewPager(viewPager);
            navigationDrawer();
        }
    }

    private void fetchTipsUrl(Map<String, String> map) {
        Call<UrlModelList> call = apiInterface.fetchUrls(map);
        call.enqueue(new Callback<UrlModelList>() {
            @Override
            public void onResponse(@NonNull Call<UrlModelList> call, @NonNull Response<UrlModelList> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getData() != null) {
                        for (UrlModel urlModel : response.body().getData()) {
                            tipsUrl = urlModel.getUrl();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UrlModelList> call, @NonNull Throwable t) {

            }
        });
    }

    private void Set_Visibility_OFF() {
        binding.lottieHome.setVisibility(View.VISIBLE);
        binding.tvNotConnected.setVisibility(View.VISIBLE);
        binding.lottieContact.setVisibility(View.GONE);
        binding.viewPager.setVisibility(View.GONE);
        binding.tabs.setVisibility(View.GONE);
        disableNavItems();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc = GoogleSignIn.getClient(this, gso);
        loading = CommonMethods.getDialog(HomeActivity.this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        navigationView = binding.navigation;
        apiInterface = ApiWebServices.getApiInterface();
        navMenu = binding.navMenu;
        bundle = new Bundle();
        drawerLayout = binding.drawerLayout;
        getLifecycle().addObserver(showAds);

        // Setting Version Code
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String version = pInfo.versionName;
            binding.versionCode.setText("Version : " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Internet checking Condition
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);
        Intent serviceIntent = new Intent(this, MyReceiver.class);
        startService(serviceIntent);
        if (isOnline(getApplicationContext())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Set_Visibility_ON();
            }
        } else {
            Set_Visibility_OFF();
        }

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        sectionsPagerAdapter.addFragment(new LoanTypeFragment(), "Loan Types");
        sectionsPagerAdapter.addFragment(new NewsFragment(), "News");
        sectionsPagerAdapter.addFragment(new TipsFragment(), "Tips");
        sectionsPagerAdapter.addFragment(new StatusFragment(), "Status");


        binding.lottieContact.setOnClickListener(view -> {
            try {
                CommonMethods.whatsApp(HomeActivity.this);
            } catch (UnsupportedEncodingException | PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void navigationDrawer() {
        navigationView = findViewById(R.id.navigation);
        navigationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        navigationView.setCheckedItem(R.id.nav_home);
        categoryContainer = findViewById(R.id.container_layout);

        navMenu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            else drawerLayout.openDrawer(GravityCompat.START);
        });
        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(Color.parseColor("#DEE4EA"));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                categoryContainer.setScaleX(offsetScale);
                categoryContainer.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = categoryContainer.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                categoryContainer.setTranslationX(xTranslation);
            }
        });
    }

    public boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case R.id.nav_finance_tips:
                openWebPage(tipsUrl);
                break;
            case R.id.nav_contact:
                try {
                    CommonMethods.whatsApp(HomeActivity.this);
                } catch (UnsupportedEncodingException | PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.nav_rate:
                CommonMethods.rateApp(getApplicationContext());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Rate");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Rate click");
                mFirebaseAnalytics.logEvent("Selected_rate_menu_item", bundle);
                break;
            case R.id.nav_privacy:
                Intent intent = new Intent(HomeActivity.this, PrivacyPolicyActivity.class);
                intent.putExtra("key", "policy");
                startActivity(intent);
                break;
            case R.id.nav_share:
                CommonMethods.shareApp(this);
                break;
            case R.id.nav_disclaimer:
                showDisclaimer();
                break;

//            case R.id.nav_signOut:
//                loading.show();
//                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "SignOut Menu");
//                mFirebaseAnalytics.logEvent("Clicked_On_SignOut_Menu", bundle);
//                // Sign Out for google user
//                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//                if (account != null) {
//                    googleSignOut();
//                }
            default:
        }
        return true;
    }

    private void showDisclaimer() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else drawerLayout.openDrawer(GravityCompat.START);
        Dialog loadingDialog;
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.disclaimer_layout);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.item_bg2));
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        TextView textView = loadingDialog.findViewById(R.id.ok);
        textView.setOnClickListener(view -> loadingDialog.dismiss());
    }

    public void disableNavItems() {
        Menu navMenu = navigationView.getMenu();
        MenuItem nav_insta = navMenu.findItem(R.id.nav_share);
        nav_insta.setEnabled(false);

        MenuItem nav_policy = navMenu.findItem(R.id.nav_privacy);
        nav_policy.setEnabled(false);

        MenuItem nav_home = navMenu.findItem(R.id.nav_home);
        nav_home.setEnabled(false);

        MenuItem nav_rate = navMenu.findItem(R.id.nav_rate);
        nav_rate.setEnabled(false);

        MenuItem nav_contact = navMenu.findItem(R.id.nav_contact);
        nav_contact.setEnabled(false);

//        MenuItem nav_signOut = navMenu.findItem(R.id.nav_signOut);
//        nav_signOut.setEnabled(false);

        MenuItem nav_tips = navMenu.findItem(R.id.nav_finance_tips);
        nav_tips.setEnabled(false);
    }

    public void enableNavItems() {
        Menu navMenu = navigationView.getMenu();
        MenuItem nav_insta = navMenu.findItem(R.id.nav_share);
        nav_insta.setEnabled(true);

        MenuItem nav_policy = navMenu.findItem(R.id.nav_privacy);
        nav_policy.setEnabled(true);

        MenuItem nav_home = navMenu.findItem(R.id.nav_home);
        nav_home.setEnabled(true);

        MenuItem nav_rate = navMenu.findItem(R.id.nav_rate);
        nav_rate.setEnabled(true);

        MenuItem nav_contact = navMenu.findItem(R.id.nav_contact);
        nav_contact.setEnabled(true);

//        MenuItem nav_signOut = navMenu.findItem(R.id.nav_signOut);
//        nav_signOut.setEnabled(true);

        MenuItem nav_tips = navMenu.findItem(R.id.nav_finance_tips);
        nav_tips.setEnabled(true);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                uri = result.getUri();
                setImage(uri, this);
                Glide.with(this).load(uri).into(StatusFragment.userImageView);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this);
        unregisterReceiver(receiver);
        showAds.destroyBanner();

    }

    @Override
    protected void onResume() {
        super.onResume();
        IronSource.onResume(this);
        registerReceiver(receiver, intentFilter);
    }

//    public void googleSignOut() {
//        gsc.signOut().addOnCompleteListener(task -> {
//            finish();
//            loading.dismiss();
//            startActivity(new Intent(HomeActivity.this, SignUpActivity.class));
//        });
//    }

    @SuppressLint("QueryPermissionsNeeded")
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Objects.requireNonNull(Paper.book().read(Prevalent.bannerTopNetworkName)).equals("IronSourceWithMeta")) {
            showAds.showTopBanner(this, binding.topAdView);

        } else if (Objects.requireNonNull(Paper.book().read(Prevalent.bannerBottomNetworkName)).equals("IronSourceWithMeta")) {
            showAds.showBottomBanner(this, binding.BottomAdview);
        }

    }


    @Override
    public void onBackPressed() {
        showAds.destroyBanner();
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
        finish();
        if (Objects.equals(Paper.book().read(Prevalent.interstitialNetwork), "AdmobWithMeta"))
            showAds.showInterstitialAds(this);

    }
}