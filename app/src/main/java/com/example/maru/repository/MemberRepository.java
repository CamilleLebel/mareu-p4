package com.example.maru.repository;

import android.util.Log;

import com.example.maru.di.DI;
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
        mMembers = mApiService.getMembers();
        mSelectedMembers = new ArrayList<>();
    }

    public List<Member> getMembers() {
        return mMembers;
    }

    public List<Member> getSelectedMembers() {
        return this.mSelectedMembers;
    }


    public boolean AddOrDeleteSelectedMember(Member member) {

        if (member.isSelected()) {
            this.mSelectedMembers.remove(member);
            member.setSelected(false);
            Log.i("DEBUG", "is not selected " + member.getFirstName());

            return false;
        }
        else {
            this.mSelectedMembers.add(member);
            member.setSelected(true);
            Log.i("DEBUG", "is selected " + member.getFirstName());

            return true;
        }
    }

}
