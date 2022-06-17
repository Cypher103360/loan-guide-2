package com.instantloanguide.allloantips.models;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

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

    @POST("fetch_news_api.php")
    Call<NewsModelList> getAllNews();

    @POST("fetch_tips_api.php")
    Call<TipsModelList> getAllTips();

    @FormUrlEncoded
    @POST("loan_fetch_ads.php")
    Call<List<AdsModel>> fetchAds(@Field("id") String id);

    @FormUrlEncoded
    @POST("fetch_banners.php")
    Call<BannerModelList> fetchBanner(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("add_loan_data.php")
    Call<MessageModel> uploadLoanData(@FieldMap Map<String, String> map);

    @GET("fetch_loan_data.php")
    Call<LoanModelList> getLoanData();

    @FormUrlEncoded
    @POST("fetch_urls.php")
    Call<UrlModelList> fetchUrls(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("fetch_loan_app_details.php")
    Call<LoanAppModelList> fetchLoanAppDetails(@Field("loanId") String id);


    @FormUrlEncoded
    @POST("upload_user_data.php")
    Call<MessageModel> uploadUserData(@FieldMap Map<String, String> map);

    @POST("fetch_own_text_url.php")
    Call<OwnTextUrlModel> fetchOwnText();

}

