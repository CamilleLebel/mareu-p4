package com.example.maru.view.activities;

import android.widget.TimePicker;

import androidx.appcompat.widget.Toolbar;

import com.example.maru.R;
import com.example.maru.view.base.BaseActivity;
import com.example.maru.view.base.BaseFragment;
import com.example.maru.view.dialogFragment.AddMeetingFragment;
import com.example.maru.view.dialogFragment.TimePickerFragmentListener;
import com.example.maru.view.fragments.CreationFragment;


public class CreationActivity extends BaseActivity implements BaseFragment.FragmentListener, TimePickerFragmentListener, AddMeetingFragment.AddMeetingDialogListener {

    // FIELDS --------------------------------------------------------------------------------------

    private CreationFragment mCreationFragment;


    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_creation;
    }

    @Override
    protected Toolbar getToolBar() {
        return null;
    }

    @Override
    protected void configureDesign() {
        this.configureAndShowMainFragment(R.id.activity_creation_frame_layout);
    }

    // INTERFACE OF FRAGMENT LISTENER **************************************************************

    @Override
    public void showMessageFromFragment(String message) {
    }

    @Override
    public void onClickFromFragment(String message) {
        // Good execution and closes the activity
        setResult(RESULT_OK);
        finish();
    }

// INTERFACE OF ADD FRAGMENT DIALOG LISTENER *******************************************************

    @Override
    public void onYesAddClicked() {
        this.mCreationFragment.onYesAddClicked();
    }

    // INTERFACE OF ON TIME PICKER FRAGMENT LISTENER ***********************************************

    @Override
    public void onTimeSet(int id, TimePicker view, int hourOfDay, int minute) {
        final String time;
        try {
            int hourInSecond = hourOfDay * 3600;
            int minuteInSecond = minute * 60;
            int timeInSecond = hourInSecond + minuteInSecond;
            time = String.valueOf(timeInSecond);
//            time = TimeTools.convertHourAndMinuteToString(hourOfDay, minute);
            this.mCreationFragment.setTextById(id, timeInSecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // FRAGMENTS ***********************************************************************************

    private void configureAndShowMainFragment(final int idOfFrameLayout) {
        this.mCreationFragment = (CreationFragment) getSupportFragmentManager().findFragmentById(idOfFrameLayout);

        // If the fragment is not displayed
        if (this.mCreationFragment == null) {
            // Creates the main fragment
            this.mCreationFragment = CreationFragment.newInstance();

            this.addFragment(idOfFrameLayout, this.mCreationFragment);
        }
    }
}
