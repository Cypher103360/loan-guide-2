package com.instantloanguide.loanguideadmin.services;

import com.instantloanguide.loanguideadmin.models.AdsModelList;
import com.instantloanguide.loanguideadmin.models.BannerModelList;
import com.instantloanguide.loanguideadmin.models.LoanAdsModel;
import com.instantloanguide.loanguideadmin.models.LoanAppModelList;
import com.instantloanguide.loanguideadmin.models.MessageModel;
import com.instantloanguide.loanguideadmin.models.NewsModelList;
import com.instantloanguide.loanguideadmin.models.OwnTextUrlModel;
import com.instantloanguide.loanguideadmin.models.TipsModelList;
import com.instantloanguide.loanguideadmin.models.UrlModelList;
import com.instantloanguide.loanguideadmin.models.UserData.UserDataModelList;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("upload_news_api.php")
    Call<MessageModel> uploadNews(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("upload_tips_api.php")
    Call<MessageModel> uploadTips(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("loan_guide_ads_update.php")
    Call<MessageModel> updateLoanAdIds(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("loan_fetch_ads.php")
    Call<List<LoanAdsModel>> fetchLoanAds(@Field("id") String id);

    @FormUrlEncoded
    @POST("upload_strip_ban_api.php")
    Call<MessageModel> uploadStripBan(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("update_news_api.php")
    Call<MessageModel> updateNews(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("update_loan_app_data_api.php")
    Call<MessageModel> updateLoanAppsData(@FieldMap Map<String, String> map);

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

    @Multipart
    @POST("update_banner.php")
    Call<MessageModel> updateBanner(@Part MultipartBody.Part idPart,
                                    @Part MultipartBody.Part imgPart,
                                    @Part MultipartBody.Part urlPart,
                                    @Part MultipartBody.Part deleteImgPart,
                                    @Part MultipartBody.Part imgKeyPart);

    @FormUrlEncoded
    @POST("upload_loan_app_data.php")
    Call<MessageModel> uploadLoanAppData(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("fetch_loan_app_details.php")
    Call<LoanAppModelList> fetchLoanAppDetails(@Field("loanId") String id);

    @FormUrlEncoded
    @POST("delete_loan_app_details.php")
    Call<MessageModel> deleteLoanAppData(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("fetch_urls.php")
    Call<UrlModelList> fetchUrls(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("update_urls.php")
    Call<MessageModel> updateUrls(@FieldMap Map<String, String> map);


    @POST("fetch_own_text_url.php")
    Call<OwnTextUrlModel> fetchOwnText();

    @POST("fetch_user_data.php")
    Call<UserDataModelList> getAllUserData();

    @FormUrlEncoded
    @POST("update_own_text.php")
    Call<MessageModel> updateOwnText(@FieldMap Map<String, String> map);

}
