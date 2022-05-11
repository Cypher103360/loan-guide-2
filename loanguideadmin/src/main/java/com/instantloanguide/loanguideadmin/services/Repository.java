package com.instantloanguide.loanguideadmin.services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.instantloanguide.loanguideadmin.models.LoanAppModel;
import com.instantloanguide.loanguideadmin.models.LoanAppModelList;
import com.instantloanguide.loanguideadmin.models.NewsModelList;
import com.instantloanguide.loanguideadmin.models.TipsModel;
import com.instantloanguide.loanguideadmin.models.TipsModelList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    ApiInterface apiInterface;
    public static Repository repository;

    MutableLiveData<NewsModelList> newsModelListMutableLiveData = new MutableLiveData<>();
    MutableLiveData<TipsModelList> tipsModelListMutableLiveData = new MutableLiveData<>();
    MutableLiveData<LoanAppModelList> loanAppModelListMutableLiveData = new MutableLiveData<>();


    public Repository() {

        apiInterface = ApiWebServices.getApiInterface();
    }
    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    public MutableLiveData<NewsModelList> getNewsModelListMutableLiveData(){
        Call<NewsModelList> call = apiInterface.getAllNews();
        call.enqueue(new Callback<NewsModelList>() {
            @Override
            public void onResponse(@NonNull Call<NewsModelList> call, @NonNull Response<NewsModelList> response) {

                if (response.isSuccessful()){
                    newsModelListMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsModelList> call,@NonNull Throwable t) {

            }
        });
        return newsModelListMutableLiveData;
    }
    public MutableLiveData<TipsModelList> getTipsModelListMutableLiveData(){
        Call<TipsModelList> call = apiInterface.getAllTips();
        call.enqueue(new Callback<TipsModelList>() {
            @Override
            public void onResponse(@NonNull Call<TipsModelList> call, @NonNull Response<TipsModelList> response) {

                if (response.isSuccessful()){
                    tipsModelListMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TipsModelList> call,@NonNull Throwable t) {

            }
        });
        return tipsModelListMutableLiveData;
    }public MutableLiveData<LoanAppModelList> getLoanAppModelListMutableLiveData(String id){
        Call<LoanAppModelList> call = apiInterface.fetchLoanAppDetails(id);
        call.enqueue(new Callback<LoanAppModelList>() {
            @Override
            public void onResponse(@NonNull Call<LoanAppModelList> call, @NonNull Response<LoanAppModelList> response) {

                if (response.isSuccessful()){
                    loanAppModelListMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoanAppModelList> call,@NonNull Throwable t) {

            }
        });
        return loanAppModelListMutableLiveData;
    }


}
