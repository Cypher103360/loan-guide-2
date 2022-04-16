package com.instantloanguide.loanguideadmin.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.instantloanguide.loanguideadmin.models.TipsModelList;
import com.instantloanguide.loanguideadmin.services.Repository;

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
