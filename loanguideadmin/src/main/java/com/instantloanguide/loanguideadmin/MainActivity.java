package com.instantloanguide.loanguideadmin;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.instantloanguide.loanguideadmin.databinding.ActivityMainBinding;
import com.instantloanguide.loanguideadmin.databinding.NewsCardLayoutBinding;
import com.instantloanguide.loanguideadmin.databinding.UploadImageDialogBinding;
import com.instantloanguide.loanguideadmin.models.AdsModel;
import com.instantloanguide.loanguideadmin.models.AdsModelList;
import com.instantloanguide.loanguideadmin.models.MessageModel;
import com.instantloanguide.loanguideadmin.services.ApiInterface;
import com.instantloanguide.loanguideadmin.services.ApiWebServices;
import com.instantloanguide.loanguideadmin.utils.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    public static String key, adIdTitle;
    Dialog dialog, loadingDialog, stripBanDialog, adsUpdateDialog;
    String titleTXt, englishTxt, hindiTxt;
    String encodedImage;
    Bitmap bitmap;
    ActivityResultLauncher<String> launcher;
    Map<String, String> map;
    ActivityMainBinding binding;
    NewsCardLayoutBinding cardLayoutBinding;
    UploadImageDialogBinding imageDialogBinding;
    ApiInterface apiInterface;
    Call<MessageModel> call;
    EditText admobKey, applovinAppKey, appOpenKey, admobBannerKey, nativeKey, banner, interstitial, networkName;
    ImageView okBtn, cancelBtn;
    AdsModel adsModel;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(binding.getRoot());

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result);
                    encodedImage = imageStore(bitmap);
                    if (key.equals("news")) {
                        cardLayoutBinding.selectImage.setImageURI(result);
                    } else imageDialogBinding.selectImage.setImageURI(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        handleClicks();

    }

    private void handleClicks() {
        binding.uploadNews.setOnClickListener(view -> {
            showNewsDialog("Upload News");
        });
        binding.uploadTips.setOnClickListener(view -> {
            showNewsDialog("Upload Tips");
        });
        binding.uploadStripImage.setOnClickListener(view -> {
            showStripBannerDialog();
        });
        binding.uploadAds.setOnClickListener(view -> {
            showUpdateAdsDialog("LoanGuide");
        });
        binding.editBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, EditActivity.class));
        });
    }

    private void showStripBannerDialog() {
        imageDialogBinding = UploadImageDialogBinding.inflate(getLayoutInflater());
        stripBanDialog = new Dialog(this);
        stripBanDialog.setContentView(imageDialogBinding.getRoot());
        stripBanDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        stripBanDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ads_item_bg));
        stripBanDialog.setCancelable(false);
        stripBanDialog.show();

        imageDialogBinding.selectImage.setOnClickListener(view -> {
            launcher.launch("image/*");
            key = "tips";
        });
        imageDialogBinding.backBtn.setOnClickListener(view -> stripBanDialog.dismiss());
        imageDialogBinding.okBtn.setOnClickListener(view -> {
            titleTXt = imageDialogBinding.itemTitle.getText().toString();

            if (encodedImage == null) {
                Toast.makeText(this, "Please Select an Image", Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(titleTXt)) {
                imageDialogBinding.itemTitle.setError("Url required!");

            } else {
                loadingDialog.show();
                map.put("img", encodedImage);
                map.put("url", titleTXt);
                uploadData(map, "Strip Banner");
            }


        });


    }

    private void init() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        loadingDialog = Util.showDialog(this);
        map = new HashMap<>();
        apiInterface = ApiWebServices.getApiInterface();

    }

    private void showNewsDialog(String dialogName) {

        dialog = new Dialog(this);
        cardLayoutBinding = NewsCardLayoutBinding.inflate(getLayoutInflater());
        dialog.setContentView(cardLayoutBinding.getRoot());
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ads_item_bg));
        dialog.setCancelable(false);
        dialog.show();
        if (dialogName.equals("Upload Tips")) {
            cardLayoutBinding.selectImage.setVisibility(View.GONE);
        } else
            cardLayoutBinding.selectImage.setVisibility(View.VISIBLE);

        cardLayoutBinding.selectImage.setOnClickListener(v -> {
            launcher.launch("image/*");
            key = "news";
        });
        cardLayoutBinding.backBtn.setOnClickListener(view -> dialog.cancel());

        cardLayoutBinding.title.setText(dialogName);

        cardLayoutBinding.okBtn.setOnClickListener(view -> {
            titleTXt = cardLayoutBinding.itemTitle.getText().toString();
            englishTxt = cardLayoutBinding.englishDesc.getText().toString();
            hindiTxt = cardLayoutBinding.hindiDesc.getText().toString();

            if (dialogName.equals("Upload Tips")) {

                if (TextUtils.isEmpty(titleTXt)) {
                    cardLayoutBinding.itemTitle.setError("Title required!");

                } else if (TextUtils.isEmpty(englishTxt)) {
                    cardLayoutBinding.englishDesc.setError("Field required!");

                } else if (TextUtils.isEmpty(hindiTxt)) {
                    cardLayoutBinding.hindiDesc.setError("Field required!");
                } else {
                    loadingDialog.show();
                    map.put("title", titleTXt);
                    map.put("englishDesc", englishTxt);
                    map.put("hindiDesc", hindiTxt);
                    uploadData(map, "Upload Tips");

                }
            } else {
                if (encodedImage == null) {
                    Toast.makeText(this, "Please Select an Image", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(titleTXt)) {
                    cardLayoutBinding.itemTitle.setError("Title required!");

                } else if (TextUtils.isEmpty(englishTxt)) {
                    cardLayoutBinding.englishDesc.setError("Field required!");

                } else if (TextUtils.isEmpty(hindiTxt)) {
                    cardLayoutBinding.hindiDesc.setError("Field required!");
                } else {
                    loadingDialog.show();
                    map.put("img", encodedImage);
                    map.put("title", titleTXt);
                    map.put("englishDesc", englishTxt);
                    map.put("hindiDesc", hindiTxt);
                    uploadData(map, "Upload News");
                }
            }


        });


    }

    private void uploadData(Map<String, String> map, String key) {
        if (key.equals("Upload News")) {
            call = apiInterface.uploadNews(map);
        } else if (key.equals("Upload Tips")) {
            call = apiInterface.uploadTips(map);
        } else if (key.equals("Strip Banner")) {
            call = apiInterface.uploadStripBan(map);
        }
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(@NonNull Call<MessageModel> call, @NonNull Response<MessageModel> response) {


                if (response.isSuccessful()) {
                    loadingDialog.dismiss();
                    if (key.equals("Strip Banner")) {
                        stripBanDialog.dismiss();
                    }
                    dialog.dismiss();
                    init();
                    Toast.makeText(MainActivity.this, "Data Uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    loadingDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<MessageModel> call, @NonNull Throwable t) {
                loadingDialog.dismiss();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void showUpdateAdsDialog(String key) {
        adsUpdateDialog = new Dialog(this);
        adsUpdateDialog.setContentView(R.layout.ad_id_layout);
        adsUpdateDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        adsUpdateDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ads_item_bg));
        adsUpdateDialog.setCancelable(false);
        adsUpdateDialog.show();
        admobKey = adsUpdateDialog.findViewById(R.id.admobKey);
        applovinAppKey = adsUpdateDialog.findViewById(R.id.appLovin_app_key);
        appOpenKey = adsUpdateDialog.findViewById(R.id.app_open_id);
        admobBannerKey = adsUpdateDialog.findViewById(R.id.admob_banner_id);
        nativeKey = adsUpdateDialog.findViewById(R.id.native_ads_id);
        banner = adsUpdateDialog.findViewById(R.id.banner_ads_id);
        interstitial = adsUpdateDialog.findViewById(R.id.interstitial_ads_id);
        networkName = adsUpdateDialog.findViewById(R.id.networkName);
        okBtn = adsUpdateDialog.findViewById(R.id.ok_btn);
        cancelBtn = adsUpdateDialog.findViewById(R.id.back_btn);
        title = adsUpdateDialog.findViewById(R.id.title);

        cancelBtn.setOnClickListener(v -> {
            adsUpdateDialog.dismiss();
        });

        apiInterface = ApiWebServices.getApiInterface();
        Call<AdsModelList> call = apiInterface.fetchAds(key);
        call.enqueue(new Callback<AdsModelList>() {
            @Override
            public void onResponse(@NonNull Call<AdsModelList> call, @NonNull Response<AdsModelList> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getData() != null) {
                        for (AdsModel ads : response.body().getData()) {
                            adsModel = ads;
                            adIdTitle = ads.getId();
                            title.setText(adIdTitle);
                            admobKey.setText(ads.getAdmobAppKey());
                            applovinAppKey.setText(ads.getAppLovinAppKey());
                            appOpenKey.setText(ads.getAppOpen());
                            admobBannerKey.setText(ads.getAdmobBanner());
                            nativeKey.setText(ads.getNativeADs());
                            banner.setText(ads.getBanner());
                            interstitial.setText(ads.getInterstitial());
                            networkName.setText(ads.getNetworkName());
                        }
                    }
                } else {
                    Log.d("adsError", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdsModelList> call, @NonNull Throwable t) {
                Log.d("adsError", t.getMessage());
            }
        });


        okBtn.setOnClickListener(v -> {
            String admobId = admobKey.getText().toString().trim();
            String ironSourceId = applovinAppKey.getText().toString().trim();
            String appOpenId = appOpenKey.getText().toString().trim();
            String admobBanner = admobBannerKey.getText().toString().trim();
            String nativeId = nativeKey.getText().toString().trim();
            String bannerId = banner.getText().toString().trim();
            String interstitialId = interstitial.getText().toString().trim();
            String network = networkName.getText().toString().trim();
            if (admobId.equals(adsModel.getAdmobAppKey())

                    && ironSourceId.equals(adsModel.getAppLovinAppKey())
                    && appOpenId.equals(adsModel.getAppOpen())
                    && admobBanner.equals(adsModel.getAdmobBanner())
                    && nativeId.equals(adsModel.getNativeADs())
                    && bannerId.equals(adsModel.getBanner())
                    && interstitialId.equals(adsModel.getInterstitial())
                    && network.equals(adsModel.getNetworkName())

            ) {

                Toast.makeText(this, "No changes made in Ids", Toast.LENGTH_SHORT).show();

            } else {


                loadingDialog.show();
                map.put("id", adIdTitle);
                map.put("admobAppKey", admobId);
                map.put("appLovinAppKey", ironSourceId);
                map.put("appOpen", appOpenId);
                map.put("admobBanner", admobBanner);
                map.put("native", nativeId);
                map.put("banner", bannerId);
                map.put("interstitial", interstitialId);
                map.put("networkName", network);
                updateAdIds(map);
            }
        });
    }

    private void updateAdIds(Map<String, String> map) {
        Call<MessageModel> call = apiInterface.updateAdIds(map);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(@NonNull Call<MessageModel> call, @NonNull Response<MessageModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();
                adsUpdateDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<MessageModel> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
    }

    public String imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageBytes = stream.toByteArray();
        return android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}