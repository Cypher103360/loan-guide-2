package com.instantloanguide.allloantips.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class NewsViewModel extends AndroidViewModel {
    Repository repository;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }

    public LiveData<NewsModelList> getAllNews(){
        return repository.getNewsModelListMutableLiveData();
    }
}
