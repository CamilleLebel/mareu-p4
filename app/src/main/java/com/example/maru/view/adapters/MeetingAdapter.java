package com.example.maru.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.models.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingViewHolder> {

    // INTERFACE -----------------------------------------------------------------------------------

    public interface MeetingAdapterListener {
        /**
         * Retrieves the position of the {@link List}
         *
         * @param position an integer that contains the position value
         */
        void onClickDeleteButton(int position);

        /**
         * Displays something when the {@link List} is empty
         *
         * @param isEmpty a boolean
         */
        void EmptyList(boolean isEmpty);
    }

    // FIELDS --------------------------------------------------------------------------------------

    private MeetingAdapterListener mCallback;
    private List<Meeting> mMeetings;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     *
     * @param callback a {@link MeetingAdapterListener} interface for the callback
     */
    public MeetingAdapter(MeetingAdapterListener callback) {
        this.mMeetings = new ArrayList<>();
        this.mCallback = callback;
    }

    // METHODS -------------------------------------------------------------------------------------

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // Creates the View thanks to the inflater
        View view = layoutInflater.inflate(MeetingViewHolder.getLayout(), parent, false);

        return new MeetingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        holder.updateMeeting(this.mMeetings.get(position), this.mCallback);
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    // MEETINGS ************************************************************************************

    /**
     * Returns the meeting at the i position
     *
     * @param position a integer that corresponds to the i position of the {@link List}
     * @return a {@link Meeting} at the i position of the {@link List}
     */
    public Meeting getMeeting(final int position) {
        return this.mMeetings.get(position);
    }

    // DATA ****************************************************************************************

    /**
     * Updates the {@link List} of {@link Meeting} and displays it
     *
     * @param newMeetings a {@link List} of {@link Meeting} that corresponds to the new data
     */
    public void updateData(final List<Meeting> newMeetings) {
        this.mMeetings = newMeetings;

        // Refreshes the RecyclerView
        notifyDataSetChanged();

        // Checks if the List of Meeting is empty (callback method)
        this.mCallback.EmptyList(this.mMeetings.isEmpty());
    }
}
