package com.example.maru.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.models.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberAdapter extends RecyclerView.Adapter<MemberViewHolder> {

    // INTERFACES ----------------------------------------------------------------------------------

    public interface MemberAdapterListener {

        void onClickCheckBox(int position);

    }

    // FIELDS --------------------------------------------------------------------------------------

    private List<Member> mMembers;
    private List<Member> mSelectedMembers;
    private MemberAdapterListener mCallback;

// CONSTRUCTORS --------------------------------------------------------------------------------

    public MemberAdapter(MemberAdapterListener callback) {
        mMembers = new ArrayList<>();
        mCallback = callback;
    }

    // METHODS -------------------------------------------------------------------------------------

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(MemberViewHolder.getLayout(), parent, false);

        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        holder.updateMember(this.mMembers.get(position), this.mCallback);
        holder.mCheckBox.setChecked(this.mMembers.get(position).isSelected());

        Log.i("DEBUG", "bind view" + mMembers.get(position).getFirstName());
    }

    @Override
    public int getItemCount() {
        return mMembers.size();
    }

    // MEMBERS *************************************************************************************

    public Member getMember(final int position) {
        return this.mMembers.get(position);
    }

    // DATA ****************************************************************************************

    public void updateData(final List<Member> newMembers) {
        this.mMembers = newMembers;

        // Refreshes the RecyclerView
        notifyDataSetChanged();
    }
}
