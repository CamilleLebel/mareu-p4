package com.example.maru.view.dialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.maru.models.Meeting;
import com.example.maru.view.base.BaseDialogFragment;


public class DeleteMeetingFragment extends BaseDialogFragment {

    // INTERFACE -----------------------------------------------------------------------------------

    public interface DeleteMeetingDialogListener {
        void onYesClicked(Meeting meeting);
    }

    // FIELDS --------------------------------------------------------------------------------------

    private DeleteMeetingDialogListener listener;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public DeleteMeetingFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Meeting meeting = getArguments().getParcelable("meeting_to_del");

        return configureAlertDialog(meeting).create();
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

    public static DeleteMeetingFragment newInstance(Meeting meeting) {
        DeleteMeetingFragment fragment = new DeleteMeetingFragment();

        Bundle args = new Bundle();
        args.putParcelable("meeting_to_del", meeting);
        fragment.setArguments(args);
        return fragment;
    }

    private AlertDialog.Builder configureAlertDialog(Meeting meeting) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warning !")
                .setMessage("Do you want to delete this Meeting: " + meeting.getTopic() + " ?")
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Yes", (dialog, which) -> listener.onYesClicked(meeting));
        return builder;
    }
}
