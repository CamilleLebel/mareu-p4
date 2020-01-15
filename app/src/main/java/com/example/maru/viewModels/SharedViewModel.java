package com.example.maru.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class SharedViewModel extends ViewModel {

    private MutableLiveData<String> textTime = new MutableLiveData<>();

    public void setTextTime(String time) {
        textTime.setValue(time);
    }

    public LiveData<String> getTextTime() {
        return textTime;
    }

}
