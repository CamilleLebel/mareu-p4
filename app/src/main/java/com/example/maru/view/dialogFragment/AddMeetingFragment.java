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

public class AddMeetingFragment extends BaseDialogFragment {
    // INTERFACE -----------------------------------------------------------------------------------

    public interface AddMeetingDialogListener {
        void onYesAddClicked();
    }

    // FIELDS --------------------------------------------------------------------------------------

    private AddMeetingDialogListener listener;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public AddMeetingFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Meeting meeting = getArguments().getParcelable("meeting_to_add");

        return configureAlertDialog(meeting).create();
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

    public static AddMeetingFragment newInstance(Meeting meeting) {
        AddMeetingFragment fragment = new AddMeetingFragment();

        Bundle args = new Bundle();
        args.putParcelable("meeting_to_add", meeting);
        fragment.setArguments(args);
        return fragment;
    }

    private AlertDialog.Builder configureAlertDialog(Meeting meeting) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.creation_of_meeting)
                .setMessage("Do you want to create this Meeting: " + meeting.getTopic() + "?")
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Yes", (dialog, which) -> listener.onYesAddClicked());
        return builder;
    }
}
