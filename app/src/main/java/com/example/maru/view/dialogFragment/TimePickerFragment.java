package com.example.maru.view.dialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.maru.view.base.BaseDialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends BaseDialogFragment implements TimePickerDialog.OnTimeSetListener {

    // FIELDS --------------------------------------------------------------------------------------

    private int mId;

    private static final String BUNDLE_KEY_ID = "BUNDLE_KEY_ID";

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public TimePickerFragment() { }

    // METHODS -------------------------------------------------------------------------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.retrieveArgument();

        Calendar calendar = Calendar.getInstance();

        final int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getContext(), this, hourOfDay, minute, DateFormat.is24HourFormat(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Configures the callback to the parent activity
        this.configureCallbackToParentActivity(context);
    }

    @Override
    public void onDetach() {
        // To prevent memory leaks
        this.mTimePickerFragmentListener = null;

        super.onDetach();
    }


    // INTERFACE OF ON TIME SET LISTENER ***********************************************************

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.mTimePickerFragmentListener.onTimeSet(this.mId, view, hourOfDay, minute);
    }

    // INSTANCES ***********************************************************************************

    public static TimePickerFragment newInstance(int id) {
        TimePickerFragment fragment = new TimePickerFragment();

        // Bundle
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_ID, id);

        fragment.setArguments(bundle);

        return fragment;
    }

    // ARGUMENTS ***********************************************************************************
    /**
     * Retrieves the argument
     */
    private void retrieveArgument() {
        this.mId = getArguments().getInt(BUNDLE_KEY_ID, 0);
    }


    // CALLBACK OF ACTIVITY ************************************************************************

    private void configureCallbackToParentActivity(Context context) {
        // Initializes the callback field
        try {
            this.mTimePickerFragmentListener = (TimePickerFragmentListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " must implement TimePickerFragmentListener");
        }
    }
}
