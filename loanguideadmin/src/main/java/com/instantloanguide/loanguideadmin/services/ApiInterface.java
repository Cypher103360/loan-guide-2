package com.instantloanguide.loanguideadmin.services;

import com.instantloanguide.loanguideadmin.models.AdsModelList;
import com.instantloanguide.loanguideadmin.models.BannerModelList;
import com.instantloanguide.loanguideadmin.models.LoanAppModelList;
import com.instantloanguide.loanguideadmin.models.MessageModel;
import com.instantloanguide.loanguideadmin.models.NewsModelList;
import com.instantloanguide.loanguideadmin.models.TipsModelList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("upload_news_api.php")
    Call<MessageModel> uploadNews(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("upload_tips_api.php")
    Call<MessageModel> uploadTips(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("upload_strip_ban_api.php")
    Call<MessageModel> uploadStripBan(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("update_news_api.php")
    Call<MessageModel> updateNews(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("update_tips_api.php")
    Call<MessageModel> updateTips(@FieldMap Map<String, String> map);

    @POST("fetch_news_api.php")
    Call<NewsModelList> getAllNews();

    @POST("fetch_tips_api.php")
    Call<TipsModelList> getAllTips();

    @FormUrlEncoded
    @POST("delete_news.php")
    Call<MessageModel> deleteNews(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("delete_tips.php")
    Call<MessageModel> deleteTips(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("ads_id_fetch.php")
    Call<AdsModelList> fetchAds(@Field("id") String id);

    @FormUrlEncoded
    @POST("ads_id_update.php")
    Call<MessageModel> updateAdIds(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("fetch_banners.php")
    Call<BannerModelList> fetchBanner(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("update_banner.php")
    Call<MessageModel> updateBanner(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("upload_loan_app_data.php")
    Call<MessageModel> uploadLoanAppData(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("fetch_loan_app_details.php")
    Call<LoanAppModelList> fetchLoanAppDetails(@Field("loanId") String id);

    @FormUrlEncoded
    @POST("delete_loan_app_details.php")
    Call<MessageModel> deleteLoanAppData(@FieldMap Map<String, String> map);
}
