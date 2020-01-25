package com.example.maru.view.base;

import androidx.fragment.app.DialogFragment;

import com.example.maru.view.dialogFragment.DeleteMeetingFragment.DeleteMeetingDialogListener;
import com.example.maru.view.dialogFragment.TimePickerFragmentListener;

import static com.example.maru.view.dialogFragment.AddRoomFilterFragment.*;
import static com.example.maru.view.dialogFragment.AddMeetingFragment.*;

public abstract class BaseDialogFragment extends DialogFragment {

    // FIELDS --------------------------------------------------------------------------------------

    protected TimePickerFragmentListener mTimePickerFragmentListener;
    protected DeleteMeetingDialogListener mDeleteMeetingDialogListener;
    protected AddMeetingDialogListener mAddMeetingDialogListener;
    protected AddRoomFilterDialogListener mAddRoomFilterDialogListener;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public BaseDialogFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

}
