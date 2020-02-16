package com.example.maru.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class SharedViewModel extends ViewModel {

    private MutableLiveData<Integer> timeLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFilter = new MutableLiveData<>();
    private MutableLiveData<Integer> minTimeLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> maxTimeLiveData = new MutableLiveData<>();

    // TIME IN CREATION FRAGMENT

    public void setTimeLiveData(int time) {
        timeLiveData.setValue(time);
    }

    public LiveData<Integer> getTimeLiveData() {
        return timeLiveData;
    }

    // IS FILTER

    public void setIsFilter(Boolean aBoolean) {
        isFilter.setValue(aBoolean);
    }

    public LiveData<Boolean> getIsFilter() {
        if (isFilter == null) {
            isFilter = new MutableLiveData<>();
        }
        return isFilter;
    }

    // MIN TIME IN FILTER FRAGMENT

    public MutableLiveData<Integer> getMinTimeLiveData() {
        return minTimeLiveData;
    }

    public void setMinTimeLiveData(int minTime) {
        this.minTimeLiveData.setValue(minTime);
    }

    // MAX TIME IN FILTER FRAGMENT

    public MutableLiveData<Integer> getMaxTimeLiveData() {
        return maxTimeLiveData;
    }

    public void setMaxTimeLiveData(int maxTime) {
        this.maxTimeLiveData.setValue(maxTime);
    }
}
