package com.example.maru.view.dialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.maru.view.base.BaseDialogFragment;

public class AddRoomFilterFragment extends BaseDialogFragment {

    // INTERFACE -----------------------------------------------------------------------------------

    public interface AddRoomFilterDialogListener {
        void onYesClicked(String roomName);
    }

    // FIELDS --------------------------------------------------------------------------------------

    private AddRoomFilterDialogListener listener;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public AddRoomFilterFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String roomName = getArguments().getString("room_name_filter");

        return configureAlertDialog(roomName).create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddRoomFilterDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement AddRoomFilterDialogListener");
        }
    }

    @Override
    public void onDetach() {
        // To prevent memory leaks
        this.mAddRoomFilterDialogListener = null;

        super.onDetach();
    }

    public static AddRoomFilterFragment newInstance(String roomName) {
        AddRoomFilterFragment fragment = new AddRoomFilterFragment();

        Bundle args = new Bundle();
        args.putString("room_name_filter", roomName);
        fragment.setArguments(args);
        return fragment;
    }

    private AlertDialog.Builder configureAlertDialog(String roomName) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Creation of room filter")
                .setMessage("Are you sure to create this filter: " + roomName + " ?")
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Yes", (dialog, which) -> listener.onYesClicked(roomName));
        return builder;
    }
}
