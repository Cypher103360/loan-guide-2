package com.instantloanguide.loanguideadmin.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.instantloanguide.loanguideadmin.models.LoanAppModelList;
import com.instantloanguide.loanguideadmin.models.NewsModelList;
import com.instantloanguide.loanguideadmin.services.Repository;

public class LoanAppViewModel extends AndroidViewModel {
    Repository repository;
    String id;

    public LoanAppViewModel(@NonNull Application application, String id) {
        super(application);
        repository = Repository.getInstance();
        this.id = id;
    }

    public LiveData<LoanAppModelList> getLoanAppDetails() {
        return repository.getLoanAppModelListMutableLiveData(id);
    }
}
