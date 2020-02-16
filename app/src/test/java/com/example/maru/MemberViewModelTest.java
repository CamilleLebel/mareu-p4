package com.example.maru;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.maru.di.DI;
import com.example.maru.models.Meeting;
import com.example.maru.models.Member;
import com.example.maru.repository.MeetingRepository;
import com.example.maru.repository.MemberRepository;
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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class MemberViewModelTest {

    private MemberViewModel mMemberViewModel;
    @Mock
    MemberRepository mMemberRepository;
    @Mock
    LifecycleOwner lifecycleOwner;

    //OBSERVER MOCK
    @Mock
    Observer<List<Member>> membersObserver;
    @Mock
    Observer<List<Member>> selectedMembersObserver;


    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        Executor executor = DI.provideExecutor();
        MockitoAnnotations.initMocks(this);
        mMemberRepository = DI.createNewMemberRepository();

        mMemberViewModel = new MemberViewModel(mMemberRepository, executor);

    //init list de members
    }

    @Test
    public void memberViewModel_getMembers() {
        mMemberViewModel.getMembers().observeForever(membersObserver);
        verify(membersObserver).onChanged(mMemberRepository.getMembers());
        assertEquals(mMemberRepository.getMembers(), mMemberViewModel.getMembers().getValue());
    }

    @Test
    public void memberViewModel_getSelectedMembers() {
        mMemberViewModel.getSelectedLDMembers().observeForever(selectedMembersObserver);
        verify(selectedMembersObserver).onChanged(mMemberRepository.getSelectedMembers());
        assertEquals(mMemberRepository.getSelectedMembers(), mMemberViewModel.getSelectedLDMembers().getValue());
    }

    @Test
    public void memberViewModel_addToSelectedMembers() {
        mMemberViewModel.getSelectedLDMembers().observeForever(selectedMembersObserver);
        Member member = mMemberRepository.getMembers().get(0);
        mMemberViewModel.addToSelectedMembers(member);
        verify(selectedMembersObserver).onChanged(mMemberRepository.getSelectedMembers());
        assertEquals(mMemberRepository.getSelectedMembers(), mMemberViewModel.getSelectedLDMembers().getValue());
    }

    @Test
    public void memberViewModel_deleteFromSelectedMembers() {
        mMemberViewModel.getSelectedLDMembers().observeForever(selectedMembersObserver);
        Member member = mMemberRepository.getMembers().get(0);
        mMemberViewModel.addToSelectedMembers(member);
        verify(selectedMembersObserver).onChanged(mMemberRepository.getSelectedMembers());
        mMemberViewModel.deleteFromSelectedMembers(member);
        verify(selectedMembersObserver).onChanged(mMemberRepository.getSelectedMembers());
        assertEquals(mMemberRepository.getSelectedMembers(), mMemberViewModel.getSelectedLDMembers().getValue());
    }

    @Test
    public void memberViewModel_deleteAllSelectedMembers() {
        mMemberViewModel.getSelectedLDMembers().observeForever(selectedMembersObserver);
        mMemberViewModel.addToSelectedMembers(mMemberRepository.getMembers().get(0));
//        mMemberViewModel.addToSelectedMembers(mMemberRepository.getMembers().get(1));
//        mMemberViewModel.addToSelectedMembers(mMemberRepository.getMembers().get(2));
//        mMemberViewModel.addToSelectedMembers(mMemberRepository.getMembers().get(3));
//        verify(selectedMembersObserver).onChanged(mMemberRepository.getSelectedMembers());
        mMemberViewModel.deleteAllSelectedMember();

        assertTrue(mMemberRepository.getSelectedMembers().isEmpty());
//        assertEquals(selectedMembersSize, mMemberViewModel.getSelectedLDMembers().getValue().size());
    }

    @After
    public void reset() {
        mMemberViewModel.getMembers().removeObservers(lifecycleOwner);
        mMemberViewModel.getSelectedLDMembers().removeObservers(lifecycleOwner);
        mMemberRepository = DI.createNewMemberRepository();
    }

}
