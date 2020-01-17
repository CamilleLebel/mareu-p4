package com.example.maru.view.base;

import android.content.Context;

import androidx.fragment.app.DialogFragment;

import com.example.maru.view.dialogFragment.DeleteMeetingFragment.DeleteMeetingDialogListener;
import com.example.maru.view.dialogFragment.TimePickerFragmentListener;

public abstract class BaseDialogFragment extends DialogFragment {

    // FIELDS --------------------------------------------------------------------------------------

    protected TimePickerFragmentListener mTimePickerFragmentListener;
    protected DeleteMeetingDialogListener mDeleteMeetingDialogListener;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public BaseDialogFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

}
