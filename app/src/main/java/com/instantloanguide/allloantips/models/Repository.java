package com.instantloanguide.allloantips.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    ApiInterface apiInterface;
    public static Repository repository;

    MutableLiveData<NewsModelList> newsModelListMutableLiveData = new MutableLiveData<>();
    MutableLiveData<TipsModelList> tipsModelListMutableLiveData = new MutableLiveData<>();


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
    }public MutableLiveData<TipsModelList> getTipsModelListMutableLiveData(){
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
    }


}
