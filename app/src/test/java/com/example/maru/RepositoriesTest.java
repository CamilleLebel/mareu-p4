package com.example.maru;

import com.example.maru.di.DI;
import com.example.maru.models.Meeting;
import com.example.maru.models.Member;
import com.example.maru.models.Room;
import com.example.maru.repository.MeetingRepository;
import com.example.maru.repository.MemberRepository;
import com.example.maru.service.ApiService;

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
public class RepositoriesTest {

    // FIELDS --------------------------------------------------------------------------------------

    private MeetingRepository mMeetingRepository;
    private MemberRepository mMemberRepository;

    private ApiService mApiService;

    // METHODS -------------------------------------------------------------------------------------

    @Before
    public void setUp() {
        mApiService = DI.getApiService();
        mMeetingRepository = DI.createNewMeetingRepository();
        mMemberRepository = DI.createNewMemberRepository();

    }

    @Test
    public void meetingRepository_getMeetings() {
        List<Meeting> meetings = mMeetingRepository.getMeetings();
        List<Meeting> expectedMeetings = mApiService.getMeetings();

        assertThat(meetings, containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void meetingRepository_addMeeting() {
        Meeting meetingToAdd = new Meeting(10, "Meeting Test", 54000, "Room 1", "maxime@lamzone.com");

        assertFalse(mMeetingRepository.getMeetings().contains(meetingToAdd));
        mMeetingRepository.addMeeting(meetingToAdd);

        assertTrue(mMeetingRepository.getMeetings().contains(meetingToAdd));
    }

    @Test
    public void meetingRepository_deleteMeeting() {
        Meeting meeting = mMeetingRepository.getMeetings().get(0);
        mMeetingRepository.deleteMeeting(meeting);

        assertFalse(mMeetingRepository.getMeetings().contains(meeting));
    }

    @Test
    public void meetingRepository_getRooms() {
        List<Room> rooms = mMeetingRepository.getRooms();
        List<Room> expectedRooms = mApiService.getRooms();

        assertThat(rooms, containsInAnyOrder(expectedRooms.toArray()));
    }

    @Test
    public void meetingRepository_filterPerRoom() {
//        List<Meeting> testFilteredMeetings = new ArrayList<>();
//        for (Meeting meeting : mMeetingRepository.getMeetings()) {
//            if (meeting.getRoom().equals("Room 1")) {
//                testFilteredMeetings.add(meeting);
//            }
//        }
        Meeting meetingToTrue = new Meeting(13, "Meeting Test", 50000, "Room 1", "maxime@lamzone.com");
        Meeting meetingToFalse = new Meeting(14, "Meeting Test", 28800, "Room 2", "benjamin@lamzone.com");
        mMeetingRepository.addMeeting(meetingToTrue);
        mMeetingRepository.addMeeting(meetingToFalse);
        mMeetingRepository.filterPerRoom("Room 1");
        List<Meeting> filteredMeetings = mMeetingRepository.getFilteredMeetings();

        assertFalse(filteredMeetings.contains(meetingToFalse));
        assertTrue(filteredMeetings.contains(meetingToTrue));
//        assertThat(testFilteredMeetings, containsInAnyOrder(filteredMeetings.toArray()));
    }

    @Test
    public void meetingRepository_filterPerHours() {
//        List<Meeting> testFilteredMeetings = new ArrayList<>();
//        for (Meeting meeting : mMeetingRepository.getMeetings()) {
//            if (meeting.getHour() > 37800 && meeting.getHour() < 54000) {
//                testFilteredMeetings.add(meeting);
//            }
//        }
        Meeting meetingToTrue = new Meeting(13, "Meeting Test", 50000, "Room 1", "maxime@lamzone.com");
        Meeting meetingToFalse = new Meeting(14, "Meeting Test", 28800, "Room 2", "benjamin@lamzone.com");
        mMeetingRepository.addMeeting(meetingToTrue);
        mMeetingRepository.addMeeting(meetingToFalse);

        mMeetingRepository.filterPerHours(37800, 54000);
        List<Meeting> filteredMeetings = mMeetingRepository.getFilteredMeetings();

        assertFalse(filteredMeetings.contains(meetingToFalse));
        assertTrue(filteredMeetings.contains(meetingToTrue));
//        assertThat(testFilteredMeetings, containsInAnyOrder(filteredMeetings.toArray()));
    }

    @Test
    public void memberRepository_getMembers() {
        List<Member> members = mMemberRepository.getMembers();
        List<Member> expectedMembers = mApiService.getMembers();

        assertThat(members, containsInAnyOrder(expectedMembers.toArray()));
    }

    @Test
    public void memberRepository_getSelectedMembers() {
        List<Member> selectedMembers = mMemberRepository.getSelectedMembers();

        assertTrue(selectedMembers.isEmpty());
    }

    @Test
    public void memberRepository_addToSelectedMembers() {
        Member member = mMemberRepository.getMembers().get(0);
        mMemberRepository.addToSelectedMembers(member);

        assertTrue(mMemberRepository.getSelectedMembers().contains(member));
    }

    @Test
    public void memberRepository_deleteFromSelectedMembers() {
        Member member = mMemberRepository.getMembers().get(1);
        mMemberRepository.addToSelectedMembers(member);
        mMemberRepository.deleteFromSelectedMembers(member);

        assertFalse(mMemberRepository.getSelectedMembers().contains(member));
    }

    @Test
    public void memberRepository_resetSelectedMembers() {
        mMemberRepository.addToSelectedMembers(mMemberRepository.getMembers().get(0));
        mMemberRepository.addToSelectedMembers(mMemberRepository.getMembers().get(1));
        mMemberRepository.addToSelectedMembers(mMemberRepository.getMembers().get(2));
        mMemberRepository.addToSelectedMembers(mMemberRepository.getMembers().get(3));
        mMemberRepository.resetSelectedMembers();

        assertTrue(mMemberRepository.getSelectedMembers().isEmpty());
    }
}
