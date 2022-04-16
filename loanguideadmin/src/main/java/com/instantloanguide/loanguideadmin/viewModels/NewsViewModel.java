package com.instantloanguide.loanguideadmin.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.instantloanguide.loanguideadmin.models.NewsModelList;
import com.instantloanguide.loanguideadmin.services.Repository;

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
