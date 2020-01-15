package com.example.maru.view.activities;

import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.maru.R;
import com.example.maru.utils.TimeTools;
import com.example.maru.view.base.BaseActivity;
import com.example.maru.view.base.BaseFragment;
import com.example.maru.view.dialogFragment.TimePickerFragmentListener;
import com.example.maru.view.fragments.CreationFragment;
import com.example.maru.viewModels.SharedViewModel;


public class CreationActivity extends BaseActivity implements BaseFragment.FragmentListener, TimePickerFragmentListener {

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

    // INTERFACE OF ON TIME PICKER FRAGMENT LISTENER ***********************************************

    @Override
    public void onTimeSet(int id, TimePicker view, int hourOfDay, int minute) {
        final String time;
        try {
            time = TimeTools.convertHourAndMinuteToString(hourOfDay, minute);
            this.mCreationFragment.setTextById(id, time);
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