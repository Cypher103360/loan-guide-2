package com.instantloanguide.allloantips.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
