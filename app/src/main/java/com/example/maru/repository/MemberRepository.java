package com.example.maru.repository;

import android.util.Log;

import com.example.maru.di.DI;
import com.example.maru.models.Meeting;
import com.example.maru.models.Member;
import com.example.maru.service.ApiService;

import java.util.ArrayList;
import java.util.List;

public class MemberRepository {

    private ApiService mApiService;
    private List<Member> mMembers;
    private List<Member> mSelectedMembers;

    public MemberRepository(ApiService service) {
        mApiService = DI.getApiService();
//        mMembers = mApiService.getMembers();

    }

    public List<Member> getMembers() {
        return mApiService.getMembers();
    }

    public List<Member> getSelectedMembers() {
        if (mSelectedMembers == null) {
            return mSelectedMembers = new ArrayList<>();
        } else {
            return this.mSelectedMembers;
        }
    }

    public void addToSelectedMembers(Member member) {
        if (mSelectedMembers.contains(member)){
            return;
        }
        mSelectedMembers.add(member);
        member.setSelected(true);
        Log.i("DEBUG", "is selected " + member.getFirstName());
    }

    public void deleteFromSelectedMembers(Member member) {
        mSelectedMembers.remove(member);
        member.setSelected(false);
        Log.i("DEBUG", "is not selected " + member.getFirstName());
    }

    public void resetSelectedMembers() {
        for (Member member : mSelectedMembers) {
            member.setSelected(false);
//            mSelectedMembers.remove(member);
        }
        mSelectedMembers = new ArrayList<>();
    }
}
