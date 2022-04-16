package com.instantloanguide.allloantips.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.instantloanguide.allloantips.BuildConfig;
import com.instantloanguide.allloantips.R;

public class CommonMethods {


    public static Dialog getDialog(Context context) {
        Dialog loadingDialog;
        loadingDialog = new Dialog(context);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.item_bg));
        loadingDialog.setCancelable(false);
        return loadingDialog;
    }

    public static void shareApp(Context context) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rateApp(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        myAppLinkToMarket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
    public static void contactUs(Context context) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setPackage("com.google.android.gm");
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"easyloanguide.loantips@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Hello");
        i.putExtra(Intent.EXTRA_TEXT, "I need some help regarding ");
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }
}
