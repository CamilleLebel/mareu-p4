package com.example.maru.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maru.models.Meeting;

public class DialogViewModel extends ViewModel {

    private MutableLiveData<Meeting> mMeetingToDelete;

    public LiveData<Meeting> getMeetingToDelete() {
        if (mMeetingToDelete == null) {
            mMeetingToDelete = new MutableLiveData<>();
        }
        return mMeetingToDelete;
    }

    public void setMeetingToDelete(Meeting meeting) {
        if (mMeetingToDelete == null) {
            mMeetingToDelete = new MutableLiveData<>();
        }
        mMeetingToDelete.setValue(meeting);
    }
}
