package com.example.maru.view.fragments;

import android.view.View;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.di.DI;
import com.example.maru.di.ViewModelFactory;
import com.example.maru.models.Meeting;
import com.example.maru.utils.ShowMessage;
import com.example.maru.view.adapters.MeetingAdapter;
import com.example.maru.view.base.BaseFragment;
import com.example.maru.view.dialogFragment.DeleteMeetingFragment;
import com.example.maru.viewModels.DialogViewModel;
import com.example.maru.viewModels.MeetingViewModel;
import com.example.maru.viewModels.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MeetingFragment extends BaseFragment implements MeetingAdapter.MeetingAdapterListener, DeleteMeetingFragment.DeleteMeetingDialogListener {

    // FIELDS --------------------------------------------------------------------------------------

    @BindView(R.id.fragment_meeting_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.fragment_meeting_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_meeting_fab_add)
    FloatingActionButton mAddFab;
    @BindView(R.id.fragment_meeting_fab_filter)
    FloatingActionButton mFilterFab;
    @BindView(R.id.fragment_meeting_tv_no_data)
    TextView mTextForNoData;

    private MeetingAdapter mAdapter;
    private boolean mIsFilter;


    // CONSTRUCTORS --------------------------------------------------------------------------------

    public MeetingFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_meeting;
    }

    @Override
    protected void configureDesign() {

        //Configure ViewModels
        this.configureViewModel();
        // Configures the RecyclerView
        this.configureRecyclerView();
        //Subscribe to View Model
        this.subscribe();
        // Updates the list of the RecyclerView
        this.updateRecyclerView(mIsFilter);

    }


// INTERFACE FRAGMENT VIEW *********************************************************************

    @Override
    public void updateRecyclerView(boolean isFilter) {

        // FILTER
        this.mIsFilter = isFilter;

        if (mIsFilter) {
            this.mSharedViewModel.setIsFilter(true);
        } else {
            this.mSharedViewModel.setIsFilter(false);
        }

        // RECYCLER VIEW
        this.mAdapter.updateData(isFilter ? this.mMeetingViewModel.getFilteredMeetings().getValue() :
                this.mMeetingViewModel.getMeetings().getValue());

        // FILTER FAB
        this.setVisibilityOfFilterFAB(isFilter);

    }

    private void subscribe() {
        final Observer<List<Meeting>> meetingsObserver = meetings -> mAdapter.updateData(meetings);
        if (mIsFilter) {
            mMeetingViewModel.getFilteredMeetings().observe(getViewLifecycleOwner(), meetingsObserver);
        } else {
            mMeetingViewModel.getMeetings().observe(getViewLifecycleOwner(), meetingsObserver);
        }
    }

    @Override
    public void setTextById(int id, int time) {

    }

    // INTERFACE MEETING ADAPTER LISTENER (CALLBACKS OF RECYCLER VIEW) *****************************

    @Override
    public void onClickDeleteButton(int position) {
        Meeting meetingToDelete = mAdapter.getMeeting(position);
        DeleteMeetingFragment.newInstance(meetingToDelete).show(getActivity().getSupportFragmentManager(), "MEETING DELETED");
    }

    @Override
    public void EmptyList(boolean isEmpty) {
        this.mTextForNoData.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

    // INTERFACE DELETE FRAGMENT DIALOG ************************************************************

    @Override
    public void onYesClicked(Meeting meeting) {
        final String message = getString(R.string.information_delete_meeting, meeting.getTopic());
        configureAndShowErrorMessage(message);
        mMeetingViewModel.deleteMeeting(meeting, mIsFilter);
        updateRecyclerView(mIsFilter);
    }

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = DI.provideViewModelFactory(getActivity());
        this.mMeetingViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MeetingViewModel.class);
        this.mDialogViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DialogViewModel.class);
        this.mSharedViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SharedViewModel.class);

    }
    // ACTIONS *************************************************************************************

    @OnClick({R.id.fragment_meeting_fab_add,
            R.id.fragment_meeting_fab_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // PHONE MODE
            case R.id.fragment_meeting_fab_add: {
                if (this.mIsFilter) {
                    // FILTER MODE
                    this.updateRecyclerView(false);
                } else {
                    // NORMAL MODE
                    this.mCallback.onClickFromFragment(null);
                }
                break;
            }
            // TABLET MODE
            case R.id.fragment_meeting_fab_filter: {
                this.updateRecyclerView(false);
                break;
            }
        }
    }

    // INSTANCES ***********************************************************************************

    /**
     * Returns a {@link MeetingFragment}
     *
     * @return a {@link MeetingFragment}
     */
    public static MeetingFragment newInstance() {
        return new MeetingFragment();
    }

    // UI ******************************************************************************************

    /**
     * Configures {@link RecyclerView} with its {@link MeetingAdapter}
     */
    private void configureRecyclerView() {
        // Adapter
        this.mAdapter = new MeetingAdapter(this);

        // RecyclerView
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (this.mSharedViewModel.getIsFilter().getValue() != null) {
            this.mIsFilter = this.mSharedViewModel.getIsFilter().getValue();
        }
    }

    // FLOATING ACTION BUTTON **********************************************************************

    /**
     * Sets the visibility of the Add {@link FloatingActionButton} thanks to the boolean in argument
     *
     * @param isVisible a boolean
     */
    public void setVisibilityOfAddFAB(boolean isVisible) {
        if (isVisible) {
            this.mAddFab.show();
        } else {
            this.mAddFab.hide();
        }
    }

    /**
     * Sets the visibility of the Filter {@link FloatingActionButton} thanks to the boolean in argument
     *
     * @param isVisible a boolean
     */
    public void setVisibilityOfFilterFAB(boolean isVisible) {
        if (isVisible) {
            this.mFilterFab.show();
        } else {
            this.mFilterFab.hide();
        }
    }

    // ERROR MESSAGES ******************************************************************************

    /**
     * Configures and show the error message
     */
    private void configureAndShowErrorMessage(final String message) {
        // IDENTIFIER W600dp
        if (getResources().getConfiguration().screenWidthDp >= getResources().getInteger(R.integer.identifier_sw600dp)) {
            this.mCallback.showMessageFromFragment(message);
        } else {
            ShowMessage.showMessageWithSnackbar(this.mCoordinatorLayout,
                    message);
        }
    }
}
