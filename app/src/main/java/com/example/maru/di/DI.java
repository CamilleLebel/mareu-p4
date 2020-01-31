package com.example.maru.di;

import android.content.Context;

import androidx.annotation.VisibleForTesting;

import com.example.maru.repository.MeetingRepository;
import com.example.maru.repository.MemberRepository;
import com.example.maru.service.ApiService;
import com.example.maru.service.DummyApiService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class DI {


    // FIELDS --------------------------------------------------------------------------------------

    private static ApiService mService = new DummyApiService();

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Returns the only instance of {@link ApiService}
     *
     * @return the only instance of {@link ApiService}
     */
    public static ApiService getApiService() {
        return mService;
    }

    public static MeetingRepository createMeetingRepository(Context context) {
        return new MeetingRepository(new DummyApiService());
    }

    public static MemberRepository createMemberRepository(Context context) {
        return new MemberRepository(new DummyApiService());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        MeetingRepository meetingRepository = createMeetingRepository(context);
        MemberRepository memberRepository = createMemberRepository(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(meetingRepository, memberRepository, executor);
    }

    /**
     * Returns always a new instance on {@link ApiService}.
     * Useful for tests, so we ensure the context is clean.
     *
     * @return a {@link ApiService}
     */
    @VisibleForTesting
    public static ApiService getNewInstanceApiService() {
        return new DummyApiService();
    }
}
