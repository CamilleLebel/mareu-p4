package com.example.maru.models;

import java.util.Objects;

public class Room {

    // FIELDS --------------------------------------------------------------------------------------

    private int mId;
    private String mName;

    /**
     * Constructor
     *
     * @param id
     * @param name
     */

    public Room(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return mId == room.mId &&
                mName.equals(room.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mName);
    }
}
