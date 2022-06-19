package com.dreamteam.teamprediction.fordreamteamcricket.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LoanModelView extends AndroidViewModel {
    Repository repository;

    public LoanModelView(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }

   public LiveData<LoanModelList> getAllLoanData(){
        return repository.getLoanModelListMutableLiveData();
    }
}
