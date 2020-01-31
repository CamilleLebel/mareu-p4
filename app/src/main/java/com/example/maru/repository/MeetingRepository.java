package com.example.maru.repository;

import com.example.maru.di.DI;
import com.example.maru.models.Meeting;
import com.example.maru.models.Room;
import com.example.maru.service.ApiService;

import java.util.ArrayList;
import java.util.List;

public class MeetingRepository {

    private ApiService mApiService;
    private List<Meeting> mMeetings;
    private List<Meeting> mFilteredMeetings;


    public MeetingRepository(ApiService service) {
        mApiService = DI.getApiService();
        mMeetings = mApiService.getMeetings();
    }

    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    public void deleteMeeting(Meeting meeting) {
        mApiService.deleteMeeting(meeting);
    }

    public void addMeeting(Meeting meeting) {
        mApiService.addMeeting(meeting);
    }

    public List<Room> getRooms() {
        return this.mApiService.getRooms();
    }

    public List<Meeting> getFilteredMeetings() {
        if (mFilteredMeetings == null) {
            mFilteredMeetings = new ArrayList<>();
        }
        return mFilteredMeetings;
    }

    public List<Meeting> filterPerRoom(String roomName) {
        List<Meeting> filteredMeetings = new ArrayList<>();
//        this.mApiService.getMeetings().stream().filter(meeting -> meeting.getRoom().equals(roomName)).collect(Collectors.toList())
        for (Meeting meeting : this.mApiService.getMeetings()) {
            if (meeting.getRoom().equals(roomName)) {
                if (mFilteredMeetings == null) {
                    mFilteredMeetings = new ArrayList<>();
                }
                filteredMeetings.add(meeting);
                mFilteredMeetings = filteredMeetings;
            }
        }

        return filteredMeetings;
    }

    public List<Meeting> filterPerHours(int minHour, int maxHour) {
        List<Meeting> filteredMeetings = new ArrayList<>();
        for (Meeting meeting : this.mApiService.getMeetings()) {
            if (meeting.getHour() > minHour && meeting.getHour() < maxHour) {
                if (mFilteredMeetings == null) {
                    mFilteredMeetings = new ArrayList<>();
                }
                filteredMeetings.add(meeting);
                mFilteredMeetings = filteredMeetings;
            }
        }
        return filteredMeetings;
    }
}
