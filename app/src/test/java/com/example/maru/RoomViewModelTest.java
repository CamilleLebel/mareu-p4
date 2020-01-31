package com.example.maru;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.maru.di.DI;
import com.example.maru.models.Meeting;
import com.example.maru.repository.MeetingRepository;
import com.example.maru.viewModels.MeetingViewModel;
import com.example.maru.viewModels.RoomViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RoomViewModelTest {

    private RoomViewModel mRoomViewModel;
    @Mock
    MeetingRepository mMeetingRepository;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        Executor executor = DI.provideExecutor();
        MockitoAnnotations.initMocks(this);
        mMeetingRepository = DI.createNewMeetingRepository();

        mRoomViewModel = new RoomViewModel(mMeetingRepository, executor);
    }

    @Test
    public void roomViewModel_getRoomsName() {
        List<String> nameOfRooms = mRoomViewModel.getRoomsName();
        List<String> expectedList = new ArrayList<>();
        expectedList.add("Room 1");
        expectedList.add("Room 2");
        expectedList.add("Room 3");
        expectedList.add("Room 4");
        expectedList.add("Room 5");
        expectedList.add("Room 6");
        expectedList.add("Room 7");
        expectedList.add("Room 8");
        expectedList.add("Room 9");
        expectedList.add("Room 10");
        assertEquals(expectedList, nameOfRooms);
    }
}
