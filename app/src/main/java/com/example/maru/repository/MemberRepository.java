package com.example.maru.repository;

import com.example.maru.di.DI;
import com.example.maru.models.Member;
import com.example.maru.service.ApiService;

import java.util.ArrayList;
import java.util.List;

public class MemberRepository {

    private ApiService mApiService;
    private List<Member> mSelectedMembers = new ArrayList<>();

    public MemberRepository(ApiService service) {
        mApiService = DI.getApiService();
    }

    public List<Member> getMembers() {
        return mApiService.getMembers();
    }

    public List<Member> getSelectedMembers() {
        return this.mSelectedMembers;
    }

    public void addToSelectedMembers(Member member) {
        mSelectedMembers.add(member);
        member.setSelected(true);
    }

    public void deleteFromSelectedMembers(Member member) {
        mSelectedMembers.remove(member);
        member.setSelected(false);
    }

    public void resetSelectedMembers() {
        for (Member member : mSelectedMembers) {
            member.setSelected(false);
        }
        mSelectedMembers = new ArrayList<>();
    }
}
