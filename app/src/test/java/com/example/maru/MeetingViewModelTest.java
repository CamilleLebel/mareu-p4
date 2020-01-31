package com.example.maru;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.example.maru.di.DI;
import com.example.maru.models.Meeting;
import com.example.maru.repository.MeetingRepository;
import com.example.maru.service.DummyGenerator;
import com.example.maru.viewModels.MeetingViewModel;
import com.example.maru.viewModels.MemberViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class MeetingViewModelTest {

    private MeetingViewModel mMeetingViewModel;
    @Mock
    MeetingRepository mMeetingRepository;
    @Mock
    LifecycleOwner lifecycleOwner;

    //OBSERVER MOCK
    @Mock
    Observer<List<Meeting>> meetingsObserver;
    @Mock
    Observer<List<Meeting>> filteredMeetingsObserver;


    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        Executor executor = DI.provideExecutor();
        MockitoAnnotations.initMocks(this);
        mMeetingRepository = DI.createNewMeetingRepository();

        mMeetingViewModel = new MeetingViewModel(mMeetingRepository, executor);


    }

    @Test
    public void meetingViewModel_getMeetings() {
        mMeetingViewModel.getMeetings().observeForever(meetingsObserver);
        verify(meetingsObserver).onChanged(mMeetingRepository.getMeetings());
        assertEquals(mMeetingRepository.getMeetings(), mMeetingViewModel.getMeetings().getValue());
    }

    @Test
    public void meetingViewModel_getFilteredMeetings() {
        mMeetingViewModel.getFilteredMeetings().observeForever(filteredMeetingsObserver);
        verify(filteredMeetingsObserver).onChanged(mMeetingRepository.getFilteredMeetings());
        assertEquals(mMeetingRepository.getFilteredMeetings(), mMeetingViewModel.getFilteredMeetings().getValue());
    }

    @Test
    public void meetingViewModel_deleteMeetingWithoutFilter() {
        mMeetingViewModel.getMeetings().observeForever(meetingsObserver);

        Meeting meetingToDelete = mMeetingRepository.getMeetings().get(0);
        int meetingSize = mMeetingRepository.getMeetings().size();
        mMeetingViewModel.deleteMeeting(meetingToDelete, false);
//        verify(meetingsObserver).onChanged(mMeetingViewModel.getMeetings().getValue());
//        assertFalse(mMeetingRepository.getMeetings().contains(meetingToDelete));
        assertEquals(meetingSize, mMeetingViewModel.getMeetings().getValue().size());
    }

    @Test
    public void meetingViewModel_deleteMeetingWithFilter() {
        mMeetingViewModel.getMeetings().observeForever(meetingsObserver);
        mMeetingViewModel.getFilteredMeetings().observeForever(filteredMeetingsObserver);

        Meeting meetingToDelete = mMeetingRepository.getMeetings().get(0);
        mMeetingViewModel.deleteMeeting(meetingToDelete, true);
        verify(meetingsObserver).onChanged(mMeetingRepository.getMeetings());
        verify(filteredMeetingsObserver).onChanged(mMeetingRepository.getFilteredMeetings());
//        assertFalse(mMeetingRepository.getMeetings().contains(meetingToDelete));
        assertFalse(mMeetingRepository.getFilteredMeetings().contains(meetingToDelete));
    }

    @Test
    public void meetingViewModel_addMeeting() {
       mMeetingViewModel.getMeetings().observeForever(meetingsObserver);

       mMeetingViewModel.addMeeting("Test Meeting", 54000, "Room 1", "bernard@lamzone.com");
       verify(meetingsObserver).onChanged(mMeetingRepository.getMeetings());
       assertEquals(mMeetingRepository.getMeetings(), mMeetingViewModel.getMeetings().getValue());
    }

    @Test
    public void meetingViewModel_filterPerRoom() {
        mMeetingViewModel.getMeetings().observeForever(meetingsObserver);

        mMeetingViewModel.filterPerRoom("Room 1");
        verify(meetingsObserver).onChanged(mMeetingRepository.getMeetings());
        assertEquals(mMeetingRepository.getFilteredMeetings(), mMeetingViewModel.getFilteredMeetings().getValue());
    }

    @Test
    public void meetingViewModel_filterPerHours() {
        mMeetingViewModel.getMeetings().observeForever(meetingsObserver);

        mMeetingViewModel.filterPerHours(37800, 54000);
        verify(meetingsObserver).onChanged(mMeetingRepository.getMeetings());
        assertEquals(mMeetingRepository.getFilteredMeetings(), mMeetingViewModel.getFilteredMeetings().getValue());
    }

    @After
    public void reset() {
        mMeetingViewModel.getMeetings().removeObservers(lifecycleOwner);
        mMeetingViewModel.getFilteredMeetings().removeObservers(lifecycleOwner);
        mMeetingRepository = DI.createNewMeetingRepository();
    }
}
