package com.example.maru.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.maru.R;
import com.example.maru.di.DI;
import com.example.maru.di.ViewModelFactory;
import com.example.maru.utils.TimeTools;
import com.example.maru.view.base.BaseFragment;
import com.example.maru.view.dialogFragment.AddHoursFilterFragment;
import com.example.maru.view.dialogFragment.AddHoursFilterFragment.AddHoursFilterDialogListener;
import com.example.maru.view.dialogFragment.TimePickerFragment;
import com.example.maru.viewModels.MeetingViewModel;
import com.example.maru.viewModels.SharedViewModel;

import butterknife.BindView;
import butterknife.OnClick;

public class HoursFilterFragment extends BaseFragment implements AddHoursFilterDialogListener {

    // FIELDS --------------------------------------------------------------------------------------

    @BindView(R.id.fragment_filter_hours_b_minimal_hour)
    Button mMinHourButton;
    @BindView(R.id.fragment_filter_hours_b_maximal_hour)
    Button mMaxHourButton;
    @BindView(R.id.fragment_filter_hours_b_filter)
    Button mFilterButton;

    public final int ID_MINIMAL_HOUR = 1;
    public final int ID_MAXIMAL_HOUR = 2;

    public int minHour;
    public int maxHour;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public HoursFilterFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_filter_hours;
    }

    @Override
    protected void configureDesign() {
        configureViewModel();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSharedViewModel.getMinTimeLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                minHour = integer;
                String timeString = TimeTools.convertSecondToString(minHour);
                mMinHourButton.setText(timeString);
            }
        });
        mSharedViewModel.getMaxTimeLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                maxHour = integer;
                String timeString = TimeTools.convertSecondToString(maxHour);
                mMaxHourButton.setText(timeString);
            }
        });
    }

    // INTERFACE FRAGMENT VIEW *********************************************************************

    @Override
    public void setTextById(int id, int time) {
        switch (id) {
            // TIME PICKER: MINIMAL HOURS
            case ID_MINIMAL_HOUR: {
                minHour = time;
                this.mSharedViewModel.setMinTimeLiveData(minHour);
                String timeString = TimeTools.convertSecondToString(minHour);
                this.mMinHourButton.setText(timeString);
                break;
            }
            // TIME PICKER: MAXIMAL HOURS
            case ID_MAXIMAL_HOUR: {
                maxHour = time;
                this.mSharedViewModel.setMaxTimeLiveData(maxHour);
                String timeString = TimeTools.convertSecondToString(time);
                this.mMaxHourButton.setText(timeString);
                break;
            }
        }
    }

    // ACTIONS *************************************************************************************

    @OnClick({R.id.fragment_filter_hours_b_minimal_hour,
            R.id.fragment_filter_hours_b_maximal_hour,
            R.id.fragment_filter_hours_b_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // SEARCH BUTTON: MINIMAL HOURS
            case R.id.fragment_filter_hours_b_minimal_hour: {
                TimePickerFragment.newInstance(ID_MINIMAL_HOUR)
                        .show(getActivity().getSupportFragmentManager(), "TIME PICKER");
                break;
            }
            // SEARCH BUTTON: MAXIMAL HOURS
            case R.id.fragment_filter_hours_b_maximal_hour: {
                TimePickerFragment.newInstance(ID_MAXIMAL_HOUR)
                        .show(getActivity().getSupportFragmentManager(), "TIME PICKER");
                break;
            }
            // FILTER BUTTON
            case R.id.fragment_filter_hours_b_filter: {
                AddHoursFilterFragment.newInstance(minHour, maxHour).show(getActivity().getSupportFragmentManager(), "hour_filter_creation");
                break;
            }
        }
    }

    // VIEWMODEL ***********************************************************************************

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = DI.provideViewModelFactory(getActivity());

        this.mMeetingViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MeetingViewModel.class);
        this.mSharedViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SharedViewModel.class);
    }

    // INSTANCES ***********************************************************************************

    public static HoursFilterFragment newInstance() {
        return new HoursFilterFragment();
    }


    @Override
    public void onYesHoursClicked(int minHour, int maxHour) {
        this.mMeetingViewModel.filterPerHours(minHour, maxHour);
        this.mCallback.onClickFromFragment(null);
    }
}
