package com.instantloanguide.loanguideadmin;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.instantloanguide.loanguideadmin.adapters.NewsAdapter;
import com.instantloanguide.loanguideadmin.adapters.NewsClickInterface;
import com.instantloanguide.loanguideadmin.adapters.TipsAdapter;
import com.instantloanguide.loanguideadmin.adapters.TipsClickInterface;
import com.instantloanguide.loanguideadmin.databinding.ActivityEditBinding;
import com.instantloanguide.loanguideadmin.databinding.EditRvLayoutBinding;
import com.instantloanguide.loanguideadmin.databinding.NewsCardLayoutBinding;
import com.instantloanguide.loanguideadmin.models.MessageModel;
import com.instantloanguide.loanguideadmin.models.NewsModel;
import com.instantloanguide.loanguideadmin.models.TipsModel;
import com.instantloanguide.loanguideadmin.services.ApiInterface;
import com.instantloanguide.loanguideadmin.services.ApiWebServices;
import com.instantloanguide.loanguideadmin.utils.Util;
import com.instantloanguide.loanguideadmin.viewModels.NewsViewModel;
import com.instantloanguide.loanguideadmin.viewModels.TipsViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity implements NewsClickInterface, TipsClickInterface {

    public static String itemId, imgPath;
    ActivityEditBinding binding;
    Dialog dialog, loadingDialog, showLoadDialog;
    EditRvLayoutBinding editRvLayoutBinding;
    LinearLayoutManager layoutManager;
    List<NewsModel> newsModels;
    NewsAdapter newsAdapter;
    NewsViewModel newsViewModel;
    List<TipsModel> tipsModels;
    TipsAdapter tipsAdapter;
    TipsViewModel tipsViewModel;
    MaterialAlertDialogBuilder builder;
    String hindiTitleTXt, engTitleTXt, url, englishTxt, hindiTxt;

    String encodedImage;
    Bitmap bitmap;
    NewsCardLayoutBinding cardLayoutBinding;
    ActivityResultLauncher<String> launcher;
    Map<String, String> map;
    ApiInterface apiInterface;
    Call<MessageModel> call;
    ItemTouchHelper.SimpleCallback simpleCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Edit your Item")
                .setMessage("Edit")
                .setNeutralButton("CANCEL", (dialog1, which) -> {

                });
        initS();
        handleClicks();

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result);
                    encodedImage = imageStore(bitmap);
                    cardLayoutBinding.selectImage.setImageURI(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void handleClicks() {
        binding.updateNews.setOnClickListener(view -> UpdateNews(this, "Update News"));
        binding.updateTips.setOnClickListener(view -> UpdateNews(this, "Update Tips"));
        binding.updateStripImage.setVisibility(View.GONE);
    }

    private void UpdateNews(Activity context, String key) {
        editRvLayoutBinding = EditRvLayoutBinding.inflate(getLayoutInflater());
        showLoadDialog = new Dialog(this);
        showLoadDialog.setContentView(editRvLayoutBinding.getRoot());
        showLoadDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        showLoadDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(EditActivity.this, R.drawable.ads_item_bg));
        showLoadDialog.setCancelable(false);
        showLoadDialog.show();
        loadingDialog.show();
        editRvLayoutBinding.title.setText(key);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        editRvLayoutBinding.editRV.setLayoutManager(layoutManager);


        if (key.equals("Update News")) {
            newsModels = new ArrayList<>();
            newsAdapter = new NewsAdapter(this, this);
            newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
            editRvLayoutBinding.editRV.setAdapter(newsAdapter);
            fetchNews();
        } else if (key.equals("Update Tips")) {
            tipsModels = new ArrayList<>();
            tipsAdapter = new TipsAdapter(this, this);
            tipsViewModel = new ViewModelProvider(this).get(TipsViewModel.class);
            editRvLayoutBinding.editRV.setAdapter(tipsAdapter);
            fetchTips();
        }

        editRvLayoutBinding.backBtn.setOnClickListener(view -> showLoadDialog.dismiss());


        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                switch (key) {
                    case "Update News":
                        itemId = newsModels.get(viewHolder.getAdapterPosition()).getId();
                        imgPath = newsModels.get(viewHolder.getAdapterPosition()).getNewsImg();
                        Log.d("ggggggggg", imgPath);
                        newsModels.remove(viewHolder.getAdapterPosition());
                        newsAdapter.updateList(newsModels);

                        map.put("id", itemId);
                        map.put("title", "News");
                        map.put("img", "news_images/" + imgPath);
                        deleteItem(map, "News");

                        break;
                    case "Update Tips":
                        itemId = tipsModels.get(viewHolder.getAdapterPosition()).getId();

                        tipsModels.remove(viewHolder.getAdapterPosition());
                        tipsAdapter.updateList(tipsModels);
                        map.put("id", itemId);
                        map.put("title", "Tips");
                        deleteItem(map, "Tips");

                        break;

                }
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(editRvLayoutBinding.editRV);


    }

    private void deleteItem(Map<String, String> map, String key) {
        if (key.equals("Tips")) {
            call = apiInterface.deleteTips(map);
        } else if (key.equals("News")) {
            call = apiInterface.deleteNews(map);
        }

        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(@NonNull Call<MessageModel> call, @NonNull Response<MessageModel> response) {
                assert response.body() != null;
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageModel> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                Log.d("onResponse", t.getMessage());
            }
        });
    }

    private void fetchTips() {
        tipsViewModel.getAllTips().observe(this, tipsModelList -> {
            if (tipsModelList != null) {
                tipsModels.clear();
                tipsModels.addAll(tipsModelList.getData());
                tipsAdapter.updateList(tipsModels);
            }
            loadingDialog.dismiss();
        });
    }

    private void fetchNews() {
        newsViewModel.getAllNews().observe(this, newsModelList -> {
            if (newsModelList != null) {
                newsModels.clear();
                newsModels.addAll(newsModelList.getData());
                newsAdapter.updateList(newsModels);
            }
            loadingDialog.dismiss();
        });
    }

    private void initS() {
        loadingDialog = Util.showDialog(this);
        map = new HashMap<>();
        apiInterface = ApiWebServices.getApiInterface();

    }

    @Override
    public void onclicked(NewsModel newsModel) {
        builder.setPositiveButton("Edit", (dialog, which) -> {

            showNewsDialog("Update News", newsModel.getId(), newsModel.getNewsImg(), newsModel.getNewsTitle(), newsModel.getNewsEngTitle(), newsModel.getUrl(), newsModel.getNewsEngDesc(), newsModel.getNewsHinDesc());
        });
        builder.show();
    }

    @Override
    public void onclicked(TipsModel tipsModel) {
        builder.setPositiveButton("Edit", (dialog, which) -> {

            showNewsDialog("Update tips", tipsModel.getId(), null, tipsModel.getTipsTitle(), tipsModel.getTipsEngTitle(), tipsModel.getTipsUrl(), tipsModel.getTipsEngDesc(), tipsModel.getTipsHinDesc());

        });
        builder.show();

    }

    private void showNewsDialog(String dialogName, String id, String newsImg, String newsTitle, String newsEngTitle, String newsUrl, String newsEngDesc, String newsHinDesc) {
        cardLayoutBinding = NewsCardLayoutBinding.inflate(getLayoutInflater());
        dialog = new Dialog(this);

        dialog.setContentView(cardLayoutBinding.getRoot());
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(EditActivity.this, R.drawable.ads_item_bg));
        dialog.setCancelable(false);
        dialog.show();
        encodedImage = newsImg;

        if (dialogName.equals("Update tips")) {
            cardLayoutBinding.selectImage.setVisibility(View.GONE);
        } else {
            Glide.with(this).load("https://gedgetsworld.in/Loan_App/news_images/" + newsImg).into(cardLayoutBinding.selectImage);
            cardLayoutBinding.selectImage.setVisibility(View.VISIBLE);
        }
        cardLayoutBinding.title.setText(dialogName);
        cardLayoutBinding.itemTitle.setText(newsTitle);
        cardLayoutBinding.itemEngTitle.setText(newsTitle);
        cardLayoutBinding.itemEngTitle.setText(newsEngTitle);
        cardLayoutBinding.itemUrl.setText(newsUrl);
        cardLayoutBinding.englishDesc.setText(newsEngDesc);
        cardLayoutBinding.hindiDesc.setText(newsHinDesc);

        cardLayoutBinding.selectImage.setOnClickListener(v -> {
            launcher.launch("image/*");
        });


        cardLayoutBinding.backBtn.setOnClickListener(view -> dialog.cancel());
        cardLayoutBinding.okBtn.setOnClickListener(view -> {

            hindiTitleTXt = cardLayoutBinding.itemTitle.getText().toString();
            engTitleTXt = cardLayoutBinding.itemEngTitle.getText().toString();
            url = cardLayoutBinding.itemUrl.getText().toString();
            englishTxt = cardLayoutBinding.englishDesc.getText().toString();
            hindiTxt = cardLayoutBinding.hindiDesc.getText().toString();

            if (dialogName.equals("Update tips")) {

                if (TextUtils.isEmpty(hindiTitleTXt)) {
                    cardLayoutBinding.itemTitle.setError("Title required!");

                }
                if (TextUtils.isEmpty(engTitleTXt)) {
                    cardLayoutBinding.itemEngTitle.setError("Title required!");

                }
                if (TextUtils.isEmpty(url)) {
                    cardLayoutBinding.itemUrl.setError("Title required!");

                } else if (TextUtils.isEmpty(englishTxt)) {
                    cardLayoutBinding.englishDesc.setError("Field required!");

                } else if (TextUtils.isEmpty(hindiTxt)) {
                    cardLayoutBinding.hindiDesc.setError("Field required!");
                } else {
                    loadingDialog.show();
                    map.put("id", id.trim());
                    map.put("title", hindiTitleTXt);
                    map.put("engTitle", engTitleTXt);
                    map.put("url", url);
                    map.put("englishDesc", englishTxt);
                    map.put("hindiDesc", hindiTxt);
                    updateData(map, "Update Tips");

                }
            } else {

                if (encodedImage == null) {
                    Toast.makeText(this, "Please Select an Image", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(hindiTitleTXt)) {
                    cardLayoutBinding.itemTitle.setError("Title required!");

                }
                if (TextUtils.isEmpty(engTitleTXt)) {
                    cardLayoutBinding.itemEngTitle.setError("Title required!");

                }
                if (TextUtils.isEmpty(url)) {
                    cardLayoutBinding.itemUrl.setError("Title required!");

                } else if (TextUtils.isEmpty(englishTxt)) {
                    cardLayoutBinding.englishDesc.setError("Field required!");

                } else if (TextUtils.isEmpty(hindiTxt)) {
                    cardLayoutBinding.hindiDesc.setError("Field required!");
                } else {
                    loadingDialog.show();
                    if (encodedImage.length() <= 150) {

                        map.put("id", id.trim());
                        map.put("img", encodedImage);
                        map.put("deleteImg", newsImg);
                        map.put("imgKey", "0");
                        map.put("title", hindiTitleTXt);
                        map.put("engTitle", engTitleTXt);
                        map.put("url", url);
                        map.put("englishDesc", englishTxt);
                        map.put("hindiDesc", hindiTxt);
                        updateData(map, "Update News");
                    }

                    if (encodedImage.length() > 100) {
                        map.put("id", id.trim());
                        map.put("img", encodedImage);
                        map.put("deleteImg", newsImg);
                        map.put("imgKey", "1");
                        map.put("title", hindiTitleTXt);
                        map.put("engTitle", engTitleTXt);
                        map.put("url", url);
                        map.put("englishDesc", englishTxt);
                        map.put("hindiDesc", hindiTxt);
                        updateData(map, "Update News");
                    }

                }


            }


        });


    }

    private void updateData(Map<String, String> map, String key) {
        if (key.equals("Update Tips")) {
            call = apiInterface.updateTips(map);
        } else if (key.equals("Update News")) {
            call = apiInterface.updateNews(map);
        }

        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(@NonNull Call<MessageModel> call, @NonNull Response<MessageModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditActivity.this, "Data Updated  " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (key.equals("Update Tips")) {
                        fetchTips();
                    } else if (key.equals("Update News")) {
                        fetchNews();
                    }
                }

                loadingDialog.dismiss();
                dialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<MessageModel> call, @NonNull Throwable t) {
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