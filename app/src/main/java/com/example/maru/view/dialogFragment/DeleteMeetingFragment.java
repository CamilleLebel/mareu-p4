package com.example.maru.view.dialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.maru.models.Meeting;
import com.example.maru.view.base.BaseDialogFragment;
import com.google.gson.Gson;


public class DeleteMeetingFragment extends BaseDialogFragment {

    // INTERFACE -----------------------------------------------------------------------------------

    public interface DeleteMeetingDialogListener {
        void onYesClicked(Meeting meeting);
    }

    // FIELDS --------------------------------------------------------------------------------------

    private DeleteMeetingDialogListener listener;
    private Meeting mMeeting;

    private static final String MEETING_TO_DELETE = "meeting_to_delete";
    // CONSTRUCTORS --------------------------------------------------------------------------------

    public DeleteMeetingFragment(Meeting meeting) {
        this.mMeeting = meeting;
    }
    public DeleteMeetingFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String json = savedInstanceState.getString(MEETING_TO_DELETE);
            if(!json.isEmpty()) {
                Gson gson = new Gson();
                Meeting meeting = gson.fromJson(json, Meeting.class);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Warning !")
                        .setMessage("Do you want to delete this Meeting ?")
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .setPositiveButton("Yes", (dialog, which) -> listener.onYesClicked(meeting));
                return builder.create();
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Warning !")
                    .setMessage("Do you want to delete this Meeting ?")
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("Yes", (dialog, which) -> listener.onYesClicked(mMeeting));
            return builder.create();
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        String json= gson.toJson(mMeeting);
        outState.putString(MEETING_TO_DELETE, json);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DeleteMeetingDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement DeleteMeetingDialogListener");
        }
    }

    @Override
    public void onDetach() {
        // To prevent memory leaks
        this.mDeleteMeetingDialogListener = null;

        super.onDetach();
    }
}
