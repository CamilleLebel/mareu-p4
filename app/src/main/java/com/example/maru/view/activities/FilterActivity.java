package com.example.maru.view.activities;

import android.widget.TimePicker;

import androidx.appcompat.widget.Toolbar;

import com.example.maru.R;
import com.example.maru.view.base.BaseActivity;
import com.example.maru.view.base.BaseFragment;
import com.example.maru.view.dialogFragment.AddHoursFilterFragment.AddHoursFilterDialogListener;
import com.example.maru.view.dialogFragment.TimePickerFragmentListener;
import com.example.maru.view.fragments.HoursFilterFragment;
import com.example.maru.view.fragments.RoomFilterFragment;

import static com.example.maru.view.dialogFragment.AddRoomFilterFragment.AddRoomFilterDialogListener;

public class FilterActivity extends BaseActivity implements BaseFragment.FragmentListener, TimePickerFragmentListener, AddRoomFilterDialogListener, AddHoursFilterDialogListener {

    // FIELDS --------------------------------------------------------------------------------------

    private int mFilterType;
    private HoursFilterFragment mHoursFilterFragment;
    private RoomFilterFragment mRoomFilterFragment;

    public static final int HOUR_FILTER = 1;
    public static final int ROOM_FILTER = 2;

    public static final String BUNDLE_EXTRA_FILTER_TYPE = "BUNDLE_EXTRA_FILTER_TYPE";

    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_filter;
    }

    @Override
    protected Toolbar getToolBar() {
        return null;
    }

    @Override
    protected void configureDesign() {
        // Retrieves the filter type sent by another
        this.retrieveValueFromIntent();

        // Selects the good filter
        this.selectFragmentToDisplay(this.mFilterType);
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
        int hourInSecond = hourOfDay * 3600;
        int minuteInSecond = minute * 60;
        int timeInSecond = hourInSecond + minuteInSecond;
        this.mHoursFilterFragment.setTextById(id, timeInSecond);
    }

    // AddRoomFilterDialogListener  ****************************************************************

    @Override
    public void onYesRoomClicked(String roomName) {
        this.mRoomFilterFragment.onYesRoomClicked(roomName);
    }

    // AddHoursFilterDialogListener  ***************************************************************

    @Override
    public void onYesHoursClicked(int minHour, int maxHour) {
        this.mHoursFilterFragment.onYesHoursClicked(minHour, maxHour);
    }

    // INTENT **************************************************************************************

    /**
     * Retrieves the filter type sent by another {@link android.app.Activity}
     */
    private void retrieveValueFromIntent() {
        this.mFilterType = getIntent().getIntExtra(BUNDLE_EXTRA_FILTER_TYPE, 0);
    }

// FRAGMENTS ***********************************************************************************

    /**
     * Selects the good filter thanks to the value in argument
     *
     * @param choice an integer that contains the choice
     */
    private void selectFragmentToDisplay(final int choice) {
        switch (choice) {
            case HOUR_FILTER: {
                this.configureAndShowHoursFilterFragment(R.id.activity_filter_frame_layout);
                break;
            }
            case ROOM_FILTER: {
                this.configureAndShowRoomFilterFragment(R.id.activity_filter_frame_layout);
                break;
            }
        }
    }

    /**
     * Configures and shows the room filter fragment (see {@link RoomFilterFragment}
     *
     * @param idOfFrameLayout an integer that contains the id value
     */
    private void configureAndShowRoomFilterFragment(final int idOfFrameLayout) {
        // Creates a Fragment [FragmentManager -> Fragment]
        this.mRoomFilterFragment = (RoomFilterFragment) getSupportFragmentManager().findFragmentById(idOfFrameLayout);

        // If the fragment is not displayed
        if (this.mRoomFilterFragment == null) {
            // Creates the main fragment
            this.mRoomFilterFragment = RoomFilterFragment.newInstance();

            this.addFragment(idOfFrameLayout, this.mRoomFilterFragment);
        }
    }


    /**
     * Configures and shows the hours filter fragment (see {@link HoursFilterFragment}
     *
     * @param idOfFrameLayout an integer that contains the id value
     */
    private void configureAndShowHoursFilterFragment(final int idOfFrameLayout) {
        // Creates a Fragment [FragmentManager -> Fragment]
        this.mHoursFilterFragment = (HoursFilterFragment) getSupportFragmentManager().findFragmentById(idOfFrameLayout);

        // If the fragment is not displayed
        if (this.mHoursFilterFragment == null) {
            // Creates the main fragment
            this.mHoursFilterFragment = HoursFilterFragment.newInstance();

            this.addFragment(idOfFrameLayout, this.mHoursFilterFragment);
        }
    }

}
