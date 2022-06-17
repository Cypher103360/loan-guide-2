package com.instantloanguide.allloantips.fragments;

import static android.content.ContentValues.TAG;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.instantloanguide.allloantips.R;
import com.instantloanguide.allloantips.adapter.LoanDataAdapter;
import com.instantloanguide.allloantips.databinding.FragmentStatusBinding;
import com.instantloanguide.allloantips.models.ApiInterface;
import com.instantloanguide.allloantips.models.ApiWebServices;
import com.instantloanguide.allloantips.models.LoanModel;
import com.instantloanguide.allloantips.models.LoanModelView;
import com.instantloanguide.allloantips.models.MessageModel;
import com.instantloanguide.allloantips.utils.CommonMethods;
import com.instantloanguide.allloantips.utils.Prevalent;
import com.instantloanguide.allloantips.utils.ShowAds;
import com.ironsource.mediationsdk.IronSource;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusFragment extends Fragment {
    public static String encodedImage;
    public static CircleImageView userImageView;
    static Bitmap bitmap;
    FragmentStatusBinding binding;
    LoanModelView loanModelView;
    Button addLoanBtn;
    Dialog addLoanDialog, loading;
    LoanDataAdapter loanDataAdapter;
    String[] storagePermission;
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    List<LoanModel> loanModels = new ArrayList<>();
    Map<String, String> map = new HashMap<>();
    ShowAds showAds = new ShowAds();


    public static String imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
        byte[] imageBytes = stream.toByteArray();
        return android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public static void setImage(Uri uri, Context context) {

        if (uri != null) {
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                userImageView.setImageBitmap(bitmap);
                encodedImage = imageStore(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatusBinding.inflate(inflater, container, false);
        loading = CommonMethods.getDialog(requireActivity());
        apiInterface = ApiWebServices.getApiInterface();
        addLoanBtn = binding.addLoanBtn;
        recyclerView = binding.loanDataRecyclerView;
        loanDataAdapter = new LoanDataAdapter(requireActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(loanDataAdapter);
        loanModelView = new ViewModelProvider(requireActivity()).get(LoanModelView.class);
        showLoanData();
        if (Paper.book().read(Prevalent.bannerTopNetworkName).equals("IronSourceWithMeta")) {
            binding.adViewTop.setVisibility(View.GONE);
            showAds.showBottomBanner(requireActivity(), binding.adViewBottom);

        } else if (Paper.book().read(Prevalent.bannerBottomNetworkName).equals("IronSourceWithMeta")) {
            binding.adViewBottom.setVisibility(View.GONE);
            showAds.showTopBanner(requireActivity(), binding.adViewTop);

        } else {
            showAds.showTopBanner(requireActivity(), binding.adViewTop);
            showAds.showBottomBanner(requireActivity(), binding.adViewBottom);
        }

        addLoanBtn.setOnClickListener(v -> AddLoanDialog(requireContext()));


        return binding.getRoot();
    }

    private void AddLoanDialog(Context context) {
        addLoanDialog = new Dialog(context);
        addLoanDialog.setContentView(R.layout.add_contest_layout);
        addLoanDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addLoanDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        addLoanDialog.setCancelable(false);
        addLoanDialog.show();

        EditText userName, loanName, loanAmount;
        Button cancelBtn, addLoanBtn;

        userName = addLoanDialog.findViewById(R.id.user_nameEDT);
        loanName = addLoanDialog.findViewById(R.id.loan_company_name);
        loanAmount = addLoanDialog.findViewById(R.id.loan_amount);
        cancelBtn = addLoanDialog.findViewById(R.id.cancel_btn);
        addLoanBtn = addLoanDialog.findViewById(R.id.upload_Loan_btn);

        userImageView = addLoanDialog.findViewById(R.id.profileImg);
        storagePermission = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        userImageView.setOnClickListener(v -> {
            if (isStoragePermissionGranted()) {
                pickFromGallery();
            }
        });
        cancelBtn.setOnClickListener(v -> {
            addLoanDialog.dismiss();
        });

        addLoanBtn.setOnClickListener(v -> {
            loading.show();
            String uName, loName, loAmount;
            uName = userName.getText().toString().trim();
            loName = loanName.getText().toString().trim();
            loAmount = loanAmount.getText().toString().trim();

            if (encodedImage == null) {
                Toast.makeText(requireActivity(), "Please select an image.", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            } else if (TextUtils.isEmpty(uName)) {
                userName.setError("Name required");
                userName.requestFocus();
            } else if (TextUtils.isEmpty(loName)) {
                loanName.setError("Field are required");
                loanName.requestFocus();
            } else if (TextUtils.isEmpty(loAmount)) {
                loanAmount.setError("Field are required");
                loanAmount.requestFocus();
            } else {
                map.put("userName", uName);
                map.put("loanName", loName);
                map.put("loanAmount", loAmount);
                map.put("img", encodedImage);
                uploadLoanData(map);
            }
        });
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PermissionChecker.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    private void uploadLoanData(Map<String, String> map) {

        Call<MessageModel> call = apiInterface.uploadLoanData(map);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(@NonNull Call<MessageModel> call, @NonNull Response<MessageModel> response) {
                assert response.body() != null;
                if (response.isSuccessful()) {
                    showLoanData();
                    Toast.makeText(requireActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    addLoanDialog.dismiss();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<MessageModel> call, @NonNull Throwable t) {
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }

    private void showLoanData() {
        loading.show();
        loanModelView.getAllLoanData().observe(requireActivity(), loanModelList -> {
            if (loanModelList.getData() != null) {
                loanModels.clear();
                loanModels.addAll(loanModelList.getData());
                loanDataAdapter.updateLoanModelList(loanModels);
                loading.dismiss();
            }
        });
    }


    private void pickFromGallery() {

        CropImage.activity().start(requireActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        IronSource.onResume(requireActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        IronSource.onPause(requireActivity());
    }
}