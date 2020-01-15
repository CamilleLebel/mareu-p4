package com.example.maru.service;

import com.example.maru.models.Meeting;
import com.example.maru.models.Member;
import com.example.maru.models.Room;

import java.util.List;

public interface ApiService {

    /**
     * @return a {@link List} of {@link Meeting}
     */
    List<Meeting> getMeetings();

    /**
     * delete a {@link Meeting}
     * @param meeting The {@link Meeting} to delete
     */
    void deleteMeeting(Meeting meeting);

    /**
     * add a {@link Meeting}
     * @param meeting The {@link Meeting} to add
     */
    void addMeeting(Meeting meeting);

    /**
     * @return a {@link List} of {@link Member}
     */
    List<Member> getMembers();

    /**
     * @return a {@link List} of {@link Room}
     */
    List<Room> getRooms();

}
