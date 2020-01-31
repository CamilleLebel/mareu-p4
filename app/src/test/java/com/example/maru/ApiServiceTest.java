package com.example.maru;

import com.example.maru.di.DI;
import com.example.maru.models.Meeting;
import com.example.maru.models.Member;
import com.example.maru.models.Room;
import com.example.maru.service.ApiService;
import com.example.maru.service.DummyGenerator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class ApiServiceTest {

    // FIELDS --------------------------------------------------------------------------------------

    private ApiService mApiService;

    // METHODS -------------------------------------------------------------------------------------

    @Before
    public void setUp() {
        this.mApiService = DI.getNewInstanceApiService();
    }

    @Test
    public void apiService_getMeetings() {
        List<Meeting> actualMeetings = this.mApiService.getMeetings();
        List<Meeting> expectedMeetings = DummyGenerator.generatorOfDummyMeetings();

        assertThat(actualMeetings, containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void apiService_deleteMeetings() {
        Meeting meeting = mApiService.getMeetings().get(0);
        mApiService.deleteMeeting(meeting);

        assertFalse(mApiService.getMeetings().contains(meeting));
    }

    @Test
    public void apiService_addMeeting() {
        Meeting meetingToAdd = new Meeting(1, "Meeting Test", 54000, "Room 1", "maxime@lamzone.com");

        assertFalse(mApiService.getMeetings().contains(meetingToAdd));
        mApiService.addMeeting(meetingToAdd);

        assertTrue(mApiService.getMeetings().contains(meetingToAdd));
    }

    @Test
    public void apiService_getRooms() {
        List<Room> rooms = mApiService.getRooms();
        List<Room> expectedRooms = DummyGenerator.generatorOfDummyRooms();

        assertThat(rooms, containsInAnyOrder(expectedRooms.toArray()));
    }

    @Test
    public void apiService_getMembers() {
        List<Member> members = mApiService.getMembers();
        List<Member> expectedMembers = DummyGenerator.generatorOfDummyMembers();

        assertThat(members, containsInAnyOrder(expectedMembers.toArray()));
    }
}
