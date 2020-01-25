package com.example.maru.view.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.models.Meeting;
import com.example.maru.utils.TimeTools;

import java.lang.ref.WeakReference;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class MeetingViewHolder extends RecyclerView.ViewHolder {

    // FIELDS --------------------------------------------------------------------------------------

    @BindView(R.id.item_meeting_iv_image)
    ImageView mImage;
    @BindView(R.id.item_meeting_tv_topic_hour_room)
    TextView mTopicHourRoom;
    @BindView(R.id.item_meeting_tv_participants)
    TextView mParticipants;
    @BindView(R.id.item_meeting_iv_delete)
    ImageButton mDeleteButton;

    private WeakReference<MeetingAdapter.MeetingAdapterListener> mListenerWeakReference;

    private int mColorRoom1, mColorRoom2, mColorRoom3, mColorRoom4, mColorRoom5, mColorRoom6,
            mColorRoom7, mColorRoom8, mColorRoom9, mColorRoom10;


    // CONSTRUCTORS --------------------------------------------------------------------------------

    public MeetingViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.retrieveColors();
    }

    // METHODS -------------------------------------------------------------------------------------

    public static int getLayout() {
        return R.layout.item_meeting;
    }

    // ACTIONS *************************************************************************************

    @OnClick(R.id.item_meeting_iv_delete)
    public void onDeleteButtonClicked() {
        MeetingAdapter.MeetingAdapterListener callback = this.mListenerWeakReference.get();

        if (callback != null) {
            callback.onClickDeleteButton(getAdapterPosition());
        }
    }

    // COLORS **************************************************************************************

    /**
     * Retrieves the colors for the {@link ImageView}
     */
    private void retrieveColors() {
        this.mColorRoom1 = ContextCompat.getColor(itemView.getContext(), R.color.colorRoom1);
        this.mColorRoom2 = ContextCompat.getColor(itemView.getContext(), R.color.colorRoom2);
        this.mColorRoom3 = ContextCompat.getColor(itemView.getContext(), R.color.colorRoom3);
        this.mColorRoom4 = ContextCompat.getColor(itemView.getContext(), R.color.colorRoom4);
        this.mColorRoom5 = ContextCompat.getColor(itemView.getContext(), R.color.colorRoom5);
        this.mColorRoom6 = ContextCompat.getColor(itemView.getContext(), R.color.colorRoom6);
        this.mColorRoom7 = ContextCompat.getColor(itemView.getContext(), R.color.colorRoom7);
        this.mColorRoom8 = ContextCompat.getColor(itemView.getContext(), R.color.colorRoom8);
        this.mColorRoom9 = ContextCompat.getColor(itemView.getContext(), R.color.colorRoom9);
        this.mColorRoom10 = ContextCompat.getColor(itemView.getContext(), R.color.colorRoom10);
    }


    // UI ******************************************************************************************

    public void updateMeeting(Meeting meeting, MeetingAdapter.MeetingAdapterListener callback) {

        //Image
        switch (meeting.getRoom()) {
            case "Room 1": {
                ((GradientDrawable) this.mImage.getBackground()).setColor(this.mColorRoom1);
                break;
            }
            case "Room 2": {
                ((GradientDrawable) this.mImage.getBackground()).setColor(this.mColorRoom2);
                break;
            }
            case "Room 3": {
                ((GradientDrawable) this.mImage.getBackground()).setColor(this.mColorRoom3);
                break;
            }
            case "Room 4": {
                ((GradientDrawable) this.mImage.getBackground()).setColor(this.mColorRoom4);
                break;
            }
            case "Room 5": {
                ((GradientDrawable) this.mImage.getBackground()).setColor(this.mColorRoom5);
                break;
            }
            case "Room 6": {
                ((GradientDrawable) this.mImage.getBackground()).setColor(this.mColorRoom6);
                break;
            }
            case "Room 7": {
                ((GradientDrawable) this.mImage.getBackground()).setColor(this.mColorRoom7);
                break;
            }
            case "Room 8": {
                ((GradientDrawable) this.mImage.getBackground()).setColor(this.mColorRoom8);
                break;
            }
            case "Room 9": {
                ((GradientDrawable) this.mImage.getBackground()).setColor(this.mColorRoom9);
                break;
            }
            case "Room 10": {
                ((GradientDrawable) this.mImage.getBackground()).setColor(this.mColorRoom10);
                break;
            }
        }

        String hourString = TimeTools.convertSecondToString(meeting.getHour());

        // TEXT VIEW
        final String topicHourRoom = meeting.getTopic() + " - " +
                hourString + " - " +
                meeting.getRoom();

        this.mTopicHourRoom.setText(topicHourRoom);

        this.mParticipants.setText(meeting.getMember());

        // LISTENER
        this.mListenerWeakReference = new WeakReference<>(callback);
    }


}
