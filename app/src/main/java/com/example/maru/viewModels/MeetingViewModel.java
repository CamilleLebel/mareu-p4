package com.example.maru.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maru.models.Meeting;
import com.example.maru.repository.MeetingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MeetingViewModel extends ViewModel {

    private MeetingRepository mMeetingRepository;
    private Executor mExecutor;

    private MutableLiveData<List<Meeting>> mMeetings;
    private MutableLiveData<List<Meeting>> mFilteredMeetingsLD;

    private static List<Meeting> mFilteredMeetings = new ArrayList<>();

    public MeetingViewModel(MeetingRepository meetingRepository, Executor executor) {
        this.mMeetingRepository = meetingRepository;
        this.mExecutor = executor;
    }

    // GET MEETINGS

    public LiveData<List<Meeting>> getMeetings() {
        if (mMeetings == null) {
            mMeetings = new MutableLiveData<>();
        }
        mMeetings.setValue(mMeetingRepository.getMeetings());

        return mMeetings;
    }

    // GET FILTERED MEETINGS

    public LiveData<List<Meeting>> getFilteredMeetings() {
        if (mFilteredMeetingsLD == null) {
            mFilteredMeetingsLD = new MutableLiveData<>();
        }
        mFilteredMeetingsLD.setValue(mFilteredMeetings);

        return mFilteredMeetingsLD;
    }

    // DELETE MEETING

    public void deleteMeeting(Meeting meeting, Boolean isFilter) {
        if (isFilter) {
            mFilteredMeetings.remove(meeting);
        }
        mExecutor.execute(() -> mMeetingRepository.deleteMeeting(meeting));
    }

    // ADD MEETING

    public String addMeeting(String topic, int hour, String room, String member) {
        Meeting meeting = new Meeting(this.mMeetingRepository.getMeetings().size() + 1,
                topic,
                hour,
                room,
                member);
        mExecutor.execute(() -> mMeetingRepository.addMeeting(meeting));
        return topic;
    }

    // FILTER PER ROOM

    public void filterPerRoom(String roomName) {
        List<Meeting> filterMeetings;

        filterMeetings = mMeetingRepository.filterPerRoom(roomName);
        mFilteredMeetings = filterMeetings;
    }

    // FILTER PER HOURS

    public void filterPerHours(int minHour, int maxHour) {
        List<Meeting> filterMeetings;

        filterMeetings = mMeetingRepository.filterPerHours(minHour, maxHour);
        mFilteredMeetings = filterMeetings;
    }
}

