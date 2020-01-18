package com.example.maru.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maru.models.Member;
import com.example.maru.repository.MemberRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class MemberViewModel extends ViewModel {

    private MemberRepository mMemberRepository;
    private Executor mExecutor;

    private MutableLiveData<List<Member>> mMembers;
    private MutableLiveData<List<Member>> mSelectedLDMembers;
    private List<Member> mSelectedMembers;


    public MemberViewModel(MemberRepository memberRepository, Executor executor) {
        this.mMemberRepository = memberRepository;
        this.mExecutor = executor;
    }

    // GET MEMBERS

    public LiveData<List<Member>> getMembers() {
        if (mMembers == null) {
            mMembers = new MutableLiveData<>();
            mMembers.setValue(mMemberRepository.getMembers());
        }
        return mMembers;
    }

    // GET SELECTED MEMBERS

    public LiveData<List<Member>> getSelectedLDMembers() {
        if (mSelectedLDMembers == null) {
            mSelectedLDMembers = new MutableLiveData<>();
            mSelectedLDMembers.setValue(mMemberRepository.getSelectedMembers());
        }
        return mSelectedLDMembers;
    }

    // ADD TO SELECTED MEMBERS

    public void addToSelectedMembers(Member member) {
        if (mSelectedMembers == null) {
            mSelectedMembers = mMemberRepository.getSelectedMembers();
        }
        mExecutor.execute(() -> mMemberRepository.addToSelectedMembers(member));
        Log.i("DEBUG", "add from selected in ViewModel " + member.getFirstName());
    }

    // DELETE FROM SELECTED MEMBERS

    public void deleteFromSelectedMembers(Member member) {
        mExecutor.execute(() -> mMemberRepository.deleteFromSelectedMembers(member));
        Log.i("DEBUG", "remove from selected in ViewModel " + member.getFirstName());
    }

    // RESET SELECTED MEMBERS

    public void resetSelectedMembers() {
        mMemberRepository.resetSelectedMembers();
        mSelectedMembers = mMemberRepository.getSelectedMembers();

        mSelectedLDMembers.setValue(mSelectedMembers);
        mMembers.setValue(mMemberRepository.getMembers());
    }

    // TURN TO FALSE PREVIOUS SELECTED MEMBERS

    public void deleteAllSelectedMember() {
        mSelectedMembers = mMemberRepository.getSelectedMembers();

        for (Member member : mSelectedMembers) {
            member.setSelected(false);
        }
    }

    // GET THE SELECTED MEMBERS STRING

    public String getSelectedMembersToString() {
        StringBuilder sb = new StringBuilder();

        int i = 0;

        for (Member member : this.mSelectedMembers) {
            sb.append(member.getEmail());

            if (++i != this.mSelectedMembers.size()) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }
}
