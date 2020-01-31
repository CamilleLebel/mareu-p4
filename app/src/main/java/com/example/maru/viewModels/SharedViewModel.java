package com.example.maru.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class SharedViewModel extends ViewModel {

    private MutableLiveData<Integer> timeLiveData = new MutableLiveData<>();

    public void setTimeLiveData(int time) {
        timeLiveData.setValue(time);
    }

    public LiveData<Integer> getTimeLiveData() {
        return timeLiveData;
    }

    private MutableLiveData<Boolean> isFilter = new MutableLiveData<>();

    public void setIsFilter(Boolean aBoolean) {
        isFilter.setValue(aBoolean);
    }

    public LiveData<Boolean> getIsFilter() {
        if (isFilter == null) {
            isFilter = new MutableLiveData<>();
        }
        return isFilter;
    }

    private MutableLiveData<Integer> minTimeLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> getMinTimeLiveData() {
        return minTimeLiveData;
    }

    public void setMinTimeLiveData(int minTime) {
        this.minTimeLiveData.setValue(minTime);
    }

    private MutableLiveData<Integer> maxTimeLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> getMaxTimeLiveData() {
        return maxTimeLiveData;
    }

    public void setMaxTimeLiveData(int maxTime) {
        this.maxTimeLiveData.setValue(maxTime);
    }
}
