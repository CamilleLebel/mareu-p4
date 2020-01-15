package com.example.maru.di;

import androidx.annotation.VisibleForTesting;

import com.example.maru.repository.MeetingRepository;
import com.example.maru.repository.MemberRepository;
import com.example.maru.service.ApiService;
import com.example.maru.service.DummyApiService;

public abstract class DI {


    // FIELDS --------------------------------------------------------------------------------------

    private static ApiService mService = new DummyApiService();

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Returns the only instance of {@link ApiService}
     * @return the only instance of {@link ApiService}
     */
    public static ApiService getApiService() {
        return mService;
    }

    public static MeetingRepository createMeetingRepository() {
        return new MeetingRepository(new DummyApiService());
    }

    public static MemberRepository createMemberRepository() {
        return new MemberRepository(new DummyApiService());
    }

    /**
     * Returns always a new instance on {@link ApiService}.
     * Useful for tests, so we ensure the context is clean.
     * @return a {@link ApiService}
     */
    @VisibleForTesting
    public static ApiService getNewInstanceApiService() {
        return new DummyApiService();
    }
}
