package com.example.maru.view.dialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.maru.R;
import com.example.maru.models.Meeting;
import com.example.maru.view.base.BaseDialogFragment;
import com.google.gson.Gson;

public class AddMeetingFragment extends BaseDialogFragment {
    // INTERFACE -----------------------------------------------------------------------------------

    public interface AddMeetingDialogListener {
        void onYesAddClicked();
    }

    // FIELDS --------------------------------------------------------------------------------------

    private AddMeetingDialogListener listener;
    private Meeting mMeeting;

    private static final String MEETING_TO_ADD = "meeting_to_add";
    // CONSTRUCTORS --------------------------------------------------------------------------------

    public AddMeetingFragment(Meeting meeting){
        this.mMeeting = meeting;
    }
    public AddMeetingFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String json = savedInstanceState.getString(MEETING_TO_ADD);
            if(!json.isEmpty()) {
                Gson gson = new Gson();
                this.mMeeting = gson.fromJson(json, Meeting.class);
            }
        }
        return configureAlertDialog().create();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        String json= gson.toJson(mMeeting);
        outState.putString(MEETING_TO_ADD, json);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddMeetingDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement DeleteMeetingDialogListener");
        }
    }

    @Override
    public void onDetach() {
        // To prevent memory leaks
        this.mAddMeetingDialogListener = null;

        super.onDetach();
    }

    private AlertDialog.Builder configureAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.creation_of_meeting)
                .setMessage("Do you want to create this Meeting ?")
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Yes", (dialog, which) -> listener.onYesAddClicked());
        return builder;
    }
}
