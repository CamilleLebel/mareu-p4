package com.example.maru;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.maru.di.DI;
import com.example.maru.repository.MeetingRepository;
import com.example.maru.viewModels.RoomViewModel;
import com.example.maru.viewModels.SharedViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class SharedViewModelTest {

    private SharedViewModel mSharedViewModel;

    @Mock
    Observer<Integer> timeObserver;
    @Mock
    Observer<Integer> minTimeObserver;
    @Mock
    Observer<Integer> maxTimeObserver;
    @Mock
    Observer<Boolean> isFilterObserver;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mSharedViewModel = new SharedViewModel();
        mSharedViewModel.getTimeLiveData().observeForever(timeObserver);
        mSharedViewModel.getMinTimeLiveData().observeForever(minTimeObserver);
        mSharedViewModel.getMaxTimeLiveData().observeForever(maxTimeObserver);
        mSharedViewModel.getIsFilter().observeForever(isFilterObserver);
        mSharedViewModel.setTimeLiveData(15000);
        mSharedViewModel.setMinTimeLiveData(37800);
        mSharedViewModel.setMaxTimeLiveData(54000);
        mSharedViewModel.setIsFilter(false);
    }

    @Test
    public void sharedViewModel_getTime() {
        verify(timeObserver).onChanged(mSharedViewModel.getTimeLiveData().getValue());
        assertEquals(15000, mSharedViewModel.getTimeLiveData().getValue().intValue());
    }

    @Test
    public void sharedViewModel_setTime() {
        mSharedViewModel.setTimeLiveData(54000);
        verify(timeObserver).onChanged(mSharedViewModel.getTimeLiveData().getValue());
        assertEquals(54000, mSharedViewModel.getTimeLiveData().getValue().intValue());
    }

    @Test
    public void sharedViewModel_getMinTime() {
        verify(minTimeObserver).onChanged(mSharedViewModel.getMinTimeLiveData().getValue());
        assertEquals(37800, mSharedViewModel.getMinTimeLiveData().getValue().intValue());
    }

    @Test
    public void sharedViewModel_setMinTime() {
        mSharedViewModel.setMinTimeLiveData(50000);
        verify(minTimeObserver).onChanged(mSharedViewModel.getMinTimeLiveData().getValue());
        assertEquals(50000, mSharedViewModel.getMinTimeLiveData().getValue().intValue());
    }

    @Test
    public void sharedViewModel_getMaxTime() {
        verify(maxTimeObserver).onChanged(mSharedViewModel.getMaxTimeLiveData().getValue());
        assertEquals(54000, mSharedViewModel.getMaxTimeLiveData().getValue().intValue());
    }

    @Test
    public void sharedViewModel_setMaxTime() {
        mSharedViewModel.setMaxTimeLiveData(65000);
        verify(maxTimeObserver).onChanged(mSharedViewModel.getMaxTimeLiveData().getValue());
        assertEquals(65000, mSharedViewModel.getMaxTimeLiveData().getValue().intValue());
    }

    @Test
    public void sharedViewModel_getIsFilter() {
        verify(isFilterObserver).onChanged(mSharedViewModel.getIsFilter().getValue());
        assertEquals(false, mSharedViewModel.getIsFilter().getValue());
    }

    @Test
    public void sharedViewModel_setIsFilter() {
        mSharedViewModel.setIsFilter(true);
        verify(isFilterObserver).onChanged(mSharedViewModel.getIsFilter().getValue());
        assertEquals(true, mSharedViewModel.getIsFilter().getValue());
    }

}
