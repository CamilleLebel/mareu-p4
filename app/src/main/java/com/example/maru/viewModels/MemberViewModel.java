package com.example.maru.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maru.di.DI;
import com.example.maru.models.Member;
import com.example.maru.repository.MemberRepository;
import com.example.maru.service.ApiService;

import java.util.ArrayList;
import java.util.List;

public class MemberViewModel extends ViewModel {

    private MemberRepository mMemberRepository;

    private MutableLiveData<List<Member>> mMembers;
    private List<Member> mSelectedMembers;


    public MemberViewModel(){
        ApiService service = DI.getApiService();
        mMemberRepository = DI.createMemberRepository();
    }

    public MutableLiveData<List<Member>> getMembers() {
        if (mMembers == null) {
            mMembers = new MutableLiveData<>();
            mMembers.setValue(mMemberRepository.getMembers());
        }
        return mMembers;
    }

    public MutableLiveData<List<Member>> resetMembers() {
        mMemberRepository = DI.createMemberRepository();
        deleteAllSelectedMember();
        mMembers.setValue(mMemberRepository.getMembers());
        return mMembers;
    }

    public void addOrDeleteSelectedMember(Member member) {

        if (mSelectedMembers == null) {
            mSelectedMembers = mMemberRepository.getSelectedMembers();
        }
        if (member.isSelected()) {
            mMemberRepository.AddOrDeleteSelectedMember(member);
//            mSelectedMembers.remove(member);
            Log.i("DEBUG", "remove from selected in ViewModel " + member.getFirstName());
        } else {
            if (!member.isSelected()) {
                mMemberRepository.AddOrDeleteSelectedMember(member);
//                mSelectedMembers.add(member);
                Log.i("DEBUG", "add from selected in ViewModel " + member.getFirstName());
            }
        }
    }

    public void deleteAllSelectedMember() {
        mSelectedMembers = mMemberRepository.getSelectedMembers();
        for (Member member : mSelectedMembers) {
            member.setSelected(false);
        }
    }


    public List<Member> getSelectedMembers() {
        if (mSelectedMembers == null) {
            return new ArrayList<>();
        } else {
            return this.mSelectedMembers;
        }
    }


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
