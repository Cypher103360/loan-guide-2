package com.instantloanguide.allloantips.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TipsViewModel extends AndroidViewModel {
    Repository repository;

    public TipsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }

    public LiveData<TipsModelList> getAllTips() {
        return repository.getTipsModelListMutableLiveData();
    }
}
