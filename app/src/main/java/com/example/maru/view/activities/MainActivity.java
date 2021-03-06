package com.example.maru.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.maru.R;
import com.example.maru.models.Meeting;
import com.example.maru.utils.ShowMessage;
import com.example.maru.view.base.BaseActivity;
import com.example.maru.view.base.BaseFragment;
import com.example.maru.view.dialogFragment.AddMeetingFragment;
import com.example.maru.view.dialogFragment.DeleteMeetingFragment;
import com.example.maru.view.dialogFragment.TimePickerFragmentListener;
import com.example.maru.view.fragments.CreationFragment;
import com.example.maru.view.fragments.MeetingFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BaseFragment.FragmentListener, TimePickerFragmentListener, DeleteMeetingFragment.DeleteMeetingDialogListener, AddMeetingFragment.AddMeetingDialogListener {

    // FIELDS --------------------------------------------------------------------------------------

    @BindView(R.id.activity_main_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private MeetingFragment mMeetingFragment;
    private CreationFragment mCreationFragment;

    public static final int REQUEST_CODE_CREATION_ACTIVITY = 100;
    public static final int REQUEST_CODE_FILTER_ACTIVITY = 200;

    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected Toolbar getToolBar() {
        return this.mToolbar;
    }

    @Override
    protected void configureDesign() {

        this.configureToolBar();

        this.configureAndShowMainFragment(R.id.activity_main_main_frame_layout);

        this.configureAndShowSecondFragment(R.id.activity_main_second_frame_layout);
    }

    // ACTIVITY ************************************************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Creates a MenuInflater to add the menu xml file to this activity
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Depending on the item Id
        switch (item.getItemId()) {
            case R.id.menu_activity_main_filter_date: {
                // Bundle
                Bundle args = new Bundle();
                args.putInt(FilterActivity.BUNDLE_EXTRA_FILTER_TYPE, FilterActivity.HOUR_FILTER);

                this.startAnotherActivityForResult(this, FilterActivity.class, args, REQUEST_CODE_FILTER_ACTIVITY);
                return true;
            }
            case R.id.menu_activity_main_filter_room: {
                // Bundle
                Bundle args = new Bundle();
                args.putInt(FilterActivity.BUNDLE_EXTRA_FILTER_TYPE, FilterActivity.ROOM_FILTER);

                this.startAnotherActivityForResult(this, FilterActivity.class, args, REQUEST_CODE_FILTER_ACTIVITY);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // CREATION ACTIVITY
        if (requestCode == REQUEST_CODE_CREATION_ACTIVITY && resultCode == RESULT_OK) {
            this.mMeetingFragment.updateRecyclerView(false);
        }
        // FILTER ACTIVITY
        if (requestCode == REQUEST_CODE_FILTER_ACTIVITY && resultCode == RESULT_OK) {
            this.mMeetingFragment.updateRecyclerView(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TABLET MODE: Hides the FAB into Fragment if there are 2 fragments displayed
        this.mMeetingFragment.setVisibilityOfAddFAB(this.mCreationFragment == null);
    }

    // INTERFACE OF FRAGMENT LISTENER **************************************************************

    @Override
    public void showMessageFromFragment(String message) {
        ShowMessage.showMessageWithSnackbar(this.mCoordinatorLayout, message);
    }

    @Override
    public void onClickFromFragment(String message) {
        if (this.mCreationFragment == null) {
            this.startAnotherActivityForResult(this, CreationActivity.class, REQUEST_CODE_CREATION_ACTIVITY);
        } else {
            this.mMeetingFragment.updateRecyclerView(false);
        }
    }

    // INTERFACE OF FRAGMENT DIALOG LISTENER *******************************************************

    @Override
    public void onYesClicked(Meeting meeting) {
        this.mMeetingFragment.onYesClicked(meeting);
    }

    // INTERFACE OF ADD FRAGMENT DIALOG LISTENER ***************************************************

    @Override
    public void onYesAddClicked() {
        this.mCreationFragment.onYesAddClicked();
    }

    // INTERFACE OF ON TIME PICKER FRAGMENT LISTENER ***********************************************

    @Override
    public void onTimeSet(int id, TimePicker view, int hourOfDay, int minute) {
        int hourInSecond = hourOfDay * 3600;
        int minuteInSecond = minute * 60;
        int timeInSecond = hourInSecond + minuteInSecond;

        this.mCreationFragment.setTextById(id, timeInSecond);
    }

    // FRAGMENTS ***********************************************************************************

    /**
     * Configures and shows the main fragment (see {@link MeetingFragment}
     *
     * @param idOfFrameLayout an integer that contains the id value
     */
    private void configureAndShowMainFragment(final int idOfFrameLayout) {
        // Creates a Fragment [FragmentManager -> Fragment]
        this.mMeetingFragment = (MeetingFragment) getSupportFragmentManager().findFragmentById(idOfFrameLayout);

        // If the fragment is not displayed
        if (this.mMeetingFragment == null) {
            // Creates the main fragment
            this.mMeetingFragment = MeetingFragment.newInstance();

            this.addFragment(idOfFrameLayout, this.mMeetingFragment);
        }
    }

    private void configureAndShowSecondFragment(final int idOfFrameLayout) {
        // Creates a Fragment [FragmentManager -> Fragment]
        this.mCreationFragment = (CreationFragment) getSupportFragmentManager().findFragmentById(idOfFrameLayout);

        if (this.mCreationFragment == null && findViewById(R.id.activity_main_second_frame_layout) != null) {

            this.mCreationFragment = CreationFragment.newInstance();

            this.addFragment(idOfFrameLayout, this.mCreationFragment);
        }
    }
}
