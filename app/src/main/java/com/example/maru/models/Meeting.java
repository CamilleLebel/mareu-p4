package com.example.maru.models;

import java.util.Objects;

public class Meeting {

    // FIELDS --------------------------------------------------------------------------------------

    private int mId;
    private String mTopic;
    private String mHour;
    private String mRoom;
    private String mMember;


    /**
     * Constructor
     * @param id id
     * @param topic topic of the meeting
     * @param hour hour that the meeting is starting
     * @param room room of the meeting
     * @param member members of the meeting
     */
    public Meeting(int id, String topic, String hour, String room, String member) {
        mId = id;
        mTopic = topic;
        mHour = hour;
        mRoom = room;
        mMember = member;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTopic() {
        return mTopic;
    }

    public void setTopic(String topic) {
        mTopic = topic;
    }

    public String getHour() {
        return mHour;
    }

    public void setHour(String hour) {
        mHour = hour;
    }

    public String getRoom() {
        return mRoom;
    }

    public void setRoom(String room) {
        mRoom = room;
    }

    public String getMember() {
        return mMember;
    }

    public void setMember(String member) {
        mMember = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return mId == meeting.mId &&
                Objects.equals(mTopic, meeting.mTopic) &&
                Objects.equals(mHour, meeting.mHour) &&
                Objects.equals(mRoom, meeting.mRoom) &&
                Objects.equals(mMember, meeting.mMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTopic, mHour, mRoom, mMember);
    }
}
