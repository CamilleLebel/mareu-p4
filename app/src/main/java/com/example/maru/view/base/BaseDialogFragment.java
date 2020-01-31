package com.example.maru.view.base;

import androidx.fragment.app.DialogFragment;

import com.example.maru.view.dialogFragment.DeleteMeetingFragment.DeleteMeetingDialogListener;
import com.example.maru.view.dialogFragment.TimePickerFragmentListener;

import static com.example.maru.view.dialogFragment.AddHoursFilterFragment.AddHoursFilterDialogListener;
import static com.example.maru.view.dialogFragment.AddMeetingFragment.AddMeetingDialogListener;
import static com.example.maru.view.dialogFragment.AddRoomFilterFragment.AddRoomFilterDialogListener;

public abstract class BaseDialogFragment extends DialogFragment {

    // FIELDS --------------------------------------------------------------------------------------

    protected TimePickerFragmentListener mTimePickerFragmentListener;
    protected DeleteMeetingDialogListener mDeleteMeetingDialogListener;
    protected AddMeetingDialogListener mAddMeetingDialogListener;
    protected AddRoomFilterDialogListener mAddRoomFilterDialogListener;
    protected AddHoursFilterDialogListener mAddHoursFilterDialogListener;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public BaseDialogFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

}
