package com.example.maru.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.maru.models.Room;
import com.example.maru.repository.MeetingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class RoomViewModel extends ViewModel {

    private MeetingRepository mMeetingRepository;
    private Executor mExecutor;

    public RoomViewModel(MeetingRepository meetingRepository, Executor executor) {
        this.mMeetingRepository = meetingRepository;
        this.mExecutor = executor;
    }

    public List<String> getRoomsName() {
        List<String> nameOfRooms = new ArrayList<>();

        for (Room room : mMeetingRepository.getRooms()) {
            nameOfRooms.add(room.getName());
        }

        return nameOfRooms;
    }
}
