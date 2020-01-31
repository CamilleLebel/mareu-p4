package com.example.maru.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.maru.repository.MeetingRepository;
import com.example.maru.repository.MemberRepository;
import com.example.maru.viewModels.MeetingViewModel;
import com.example.maru.viewModels.MemberViewModel;
import com.example.maru.viewModels.RoomViewModel;
import com.example.maru.viewModels.SharedViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final MeetingRepository mMeetingRepository;
    private final MemberRepository mMemberRepository;
    private final Executor executor;

    public ViewModelFactory(MeetingRepository meetingRepository, MemberRepository memberRepository, Executor executor) {
        this.mMeetingRepository = meetingRepository;
        this.mMemberRepository = memberRepository;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeetingViewModel.class)) {
            return (T) new MeetingViewModel(mMeetingRepository, executor);
        }
        if (modelClass.isAssignableFrom(MemberViewModel.class)) {
            return (T) new MemberViewModel(mMemberRepository, executor);
        }
        if (modelClass.isAssignableFrom(RoomViewModel.class)) {
            return (T) new RoomViewModel(mMeetingRepository, executor);
        }
        if (modelClass.isAssignableFrom(SharedViewModel.class)) {
            return (T) new SharedViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
