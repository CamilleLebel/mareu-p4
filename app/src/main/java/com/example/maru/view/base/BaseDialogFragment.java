package com.example.maru.view.base;

import android.content.Context;

import androidx.fragment.app.DialogFragment;

import com.example.maru.view.dialogFragment.TimePickerFragmentListener;

public abstract class BaseDialogFragment extends DialogFragment {

    // FIELDS --------------------------------------------------------------------------------------

    protected TimePickerFragmentListener mListener;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public BaseDialogFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Configures the callback to the parent activity
        this.configureCallbackToParentActivity(context);
    }

    @Override
    public void onDetach() {
        // To prevent memory leaks
        this.mListener = null;

        super.onDetach();
    }

    // CALLBACK OF ACTIVITY ************************************************************************

    private void configureCallbackToParentActivity(Context context) {
        // Initializes the callback field
        try {
            this.mListener = (TimePickerFragmentListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " must implement TimePickerFragmentListener");
        }
    }

}
