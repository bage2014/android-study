package com.bage.tutorials.ui.deliver;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeliverViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DeliverViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Deliver fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}