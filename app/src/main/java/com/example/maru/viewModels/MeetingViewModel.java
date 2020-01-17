package com.example.maru.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maru.di.DI;
import com.example.maru.models.Meeting;
import com.example.maru.models.Member;
import com.example.maru.models.Room;
import com.example.maru.repository.MeetingRepository;
import com.example.maru.repository.MemberRepository;
import com.example.maru.service.ApiService;
import com.example.maru.view.View;

import java.util.ArrayList;
import java.util.List;

public class MeetingViewModel extends ViewModel {

    private MeetingRepository mMeetingRepository;

    private MutableLiveData<List<Meeting>> mMeetings;
    private ApiService mService;

    private static List<Meeting> mFilteredMeetings = new ArrayList<>();

    public MeetingViewModel(){
        mService = DI.getApiService();
        mMeetingRepository = DI.createMeetingRepository();
    }


    public LiveData<List<Meeting>> getMeetings() {
        if (mMeetings == null) {
            mMeetings = new MutableLiveData<>();
            mMeetings.setValue(mMeetingRepository.getMeetings());
        }
        return mMeetings;
    }

    public void deleteMeeting(Meeting meeting, Boolean isFilter) {

        if (isFilter) {
            mFilteredMeetings.remove(meeting);
        }

        mMeetingRepository.deleteMeeting(meeting);

    }

    public String addMeeting(String topic, String hour, String room, String member) {
        Meeting meeting = new Meeting(this.mMeetingRepository.getMeetings().size() + 1,
                topic,
                hour,
                room,
                member);

        mMeetingRepository.addMeeting(meeting);

        return topic;
    }

    public List<String> getRoomsName() {
        List<String> nameOfRooms = new ArrayList<>();

        for (Room room : mMeetingRepository.getRooms()) {
            nameOfRooms.add(room.getName());
        }

        return nameOfRooms;
    }

}
