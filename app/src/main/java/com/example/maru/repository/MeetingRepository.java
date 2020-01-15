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


    public MeetingRepository(ApiService service) {
        mApiService = DI.getApiService();
        mMeetings = mApiService.getMeetings();
    }

    public List<Meeting> getMeetings() {
        return mApiService.getMeetings();
    }

    public void deleteMeeting(Meeting meeting) {
        mApiService.deleteMeeting(meeting);
    }

    public void addMeeting(Meeting meeting) {
        mApiService.addMeeting(meeting);
    }

    public List<String> getRoomsName() {
        List<String> nameOfRooms = new ArrayList<>();

        for (Room room : this.mApiService.getRooms()) {
            nameOfRooms.add(room.getName());
        }

        return nameOfRooms;
    }

    public List<Room> getRooms() {
        return this.mApiService.getRooms();
    }
}
