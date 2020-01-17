package com.example.maru.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maru.models.Meeting;


public class SharedViewModel extends ViewModel {

    private MutableLiveData<String> textTime = new MutableLiveData<>();

    public void setTextTime(String time) {
        textTime.setValue(time);
    }

    public LiveData<String> getTextTime() {
        return textTime;
    }

    private MutableLiveData<Meeting> mMeeting = new MutableLiveData<>();

    public void setMeeting(Meeting meeting) {
        mMeeting.setValue(meeting);
    }

    public LiveData<Meeting> getMeeting() {
        return mMeeting;
    }

}
