package com.example.maru.service;

import androidx.lifecycle.MutableLiveData;

import com.example.maru.models.Meeting;
import com.example.maru.models.Member;
import com.example.maru.models.Room;

import java.util.List;

public class DummyApiService implements ApiService {

    // FIELDS --------------------------------------------------------------------------------------

    private List<Meeting> mMeetings = DummyGenerator.generatorOfDummyMeetings();
    private List<Room> mRooms = DummyGenerator.generatorOfDummyRooms();
    private List<Member> mMembers = DummyGenerator.generatorOfDummyMembers();

    // METHODS -------------------------------------------------------------------------------------

    @Override
    public List<Meeting> getMeetings() {
        return this.mMeetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        this.mMeetings.remove(meeting);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        this.mMeetings.add(meeting);
    }

    @Override
    public List<Member> getMembers() {
        return this.mMembers;
    }

    @Override
    public List<Room> getRooms() {
        return this.mRooms;
    }
}
