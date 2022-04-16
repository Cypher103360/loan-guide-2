package com.instantloanguide.loanguideadmin.utils;

import android.app.Activity;
import android.app.Dialog;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.instantloanguide.loanguideadmin.R;

public class Util {


    public static Dialog showDialog(Activity context) {
        //****Loading Dialog****/
        Dialog loadingDialog = new Dialog(context);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.item_bg));
        loadingDialog.setCancelable(false);
        return loadingDialog;
        //**Loading Dialog****/
    }
}
