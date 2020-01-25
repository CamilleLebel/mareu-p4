package com.example.maru.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.maru.models.Meeting;
import com.example.maru.models.Member;
import com.example.maru.models.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyGenerator {

    // FIELDS --------------------------------------------------------------------------------------

    private static List<Meeting> dummyMeetings = Arrays.asList(
            new Meeting(1, "Meeting A", 10000, "Room 1", "maxime@lamzone.com"),
            new Meeting(2, "Meeting B", 52000, "Room 2", "paul@lamzone.com"),
            new Meeting(3, "Meeting C", 42222, "Room 3", "amandine@lamzone.com"),
            new Meeting(4, "Meeting D", 44444, "Room 4", "maxime@lamzone.com")
    );

    private static List<Room> dummyRooms = Arrays.asList(
            new Room(1, "Room 1"),
            new Room(2, "Room 2"),
            new Room(3, "Room 3"),
            new Room(4, "Room 4"),
            new Room(5, "Room 5"),
            new Room(6, "Room 6"),
            new Room(7, "Room 7"),
            new Room(8, "Room 8"),
            new Room(9, "Room 9"),
            new Room(10, "Room 10")

    );

    private static List<Member> dummyMembers = Arrays.asList(
            new Member(1, "Maxime", "Dupond", "maxime@lamzone.com", "https://api.adorable.io/avatars/512/Maxime@adorable.io.png"),
            new Member(2, "Paul", "Patru", "paul@lamzone.com", "https://api.adorable.io/avatars/512/Paul@adorable.io.png"),
            new Member(3, "Amandine", "Delorme", "amandine@lamzone.com", "https://api.adorable.io/avatars/512/Amandine@adorable.io.png"),
            new Member(4, "Thomas", "Sola", "thomas@lamzone.com", "https://api.adorable.io/avatars/512/Thomas@adorable.io.png"),
            new Member(5, "Melina", "Zurita", "melina@lamzone.com", "https://api.adorable.io/avatars/512/Melina@adorable.io.png"),
            new Member(6, "Kevin", "Morre", "kevin@lamzone.com", "https://api.adorable.io/avatars/512/Kevin@adorable.io.png"),
            new Member(7, "Cyril", "Marcas", "cyril@lamzone.com", "https://api.adorable.io/avatars/512/Cyril@adorable.io.png"),
            new Member(8, "Gor", "Zirc", "gor@lamzone.com", "https://api.adorable.io/avatars/512/Gor@adorable.io.png"),
            new Member(9, "Pavel", "Mancel", "pavel@lamzone.com", "https://api.adorable.io/avatars/512/Pavel@adorable.io.png")
    );

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Generates the dummy meetings
     * @return a {@link List} of {@link Meeting}
     */
    public static List<Meeting> generatorOfDummyMeetings() {
        return new ArrayList<Meeting>(dummyMeetings);
    }

    /**
     * Generates the dummy rooms
     * @return a {@link List} of {@link Room}
     */
    public static List<Room> generatorOfDummyRooms() {
        return new ArrayList<>(dummyRooms);
    }

    /**
     * Generates the dummy members
     * @return a {@link List} of {@link Member}
     */
    public static List<Member> generatorOfDummyMembers() {
        return new ArrayList<>(dummyMembers);
    }
}
