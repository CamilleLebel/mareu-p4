package com.example.maru.view.dialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.maru.R;
import com.example.maru.utils.TimeTools;
import com.example.maru.view.base.BaseDialogFragment;

public class AddHoursFilterFragment extends BaseDialogFragment {

    // INTERFACE -----------------------------------------------------------------------------------

    public interface AddHoursFilterDialogListener {
        void onYesHoursClicked(int minHour, int maxHour);
    }

    // FIELDS --------------------------------------------------------------------------------------

    private AddHoursFilterFragment.AddHoursFilterDialogListener listener;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public AddHoursFilterFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int minHour = getArguments().getInt("minHour");
        int maxHour = getArguments().getInt("maxHour");

        return configureAlertDialog(minHour, maxHour).create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddHoursFilterFragment.AddHoursFilterDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement AddRoomFilterDialogListener");
        }
    }

    @Override
    public void onDetach() {
        // To prevent memory leaks
        this.mAddHoursFilterDialogListener = null;

        super.onDetach();
    }


    public static AddHoursFilterFragment newInstance(int minHour, int maxHour) {
        AddHoursFilterFragment fragment = new AddHoursFilterFragment();

        Bundle args = new Bundle();
        args.putInt("minHour", minHour);
        args.putInt("maxHour", maxHour);
        fragment.setArguments(args);
        return fragment;
    }

    private AlertDialog.Builder configureAlertDialog(int minHour, int maxHour) {
        String minHourString = TimeTools.convertSecondToString(minHour);
        String maxHourString = TimeTools.convertSecondToString(maxHour);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.creation_of_hours_filter))
                .setMessage("Do you want to create this filter ? \n"
                        +   minHourString + " to " + maxHourString)
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Yes", (dialog, which) -> listener.onYesHoursClicked(minHour, maxHour));
        return builder;
    }
}
