package com.example.maru.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.models.Meeting;
import com.example.maru.utils.ShowMessage;
import com.example.maru.view.adapters.MeetingAdapter;
import com.example.maru.view.base.BaseFragment;
import com.example.maru.viewModels.MeetingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MeetingFragment extends BaseFragment implements MeetingAdapter.MeetingAdapterListener {

    // FIELDS --------------------------------------------------------------------------------------

    @BindView(R.id.fragment_meeting_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.fragment_meeting_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_meeting_fab_add)
    FloatingActionButton mAddFab;
    @BindView(R.id.fragment_meeting_fab_filter)
    FloatingActionButton mFilterFab;

    private MeetingAdapter mAdapter;
    private boolean mIsFilter;

    private LiveData<List<Meeting>> mMeetings;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public MeetingFragment() {}

    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_meeting;
    }

    @Override
    protected void configureDesign() {
        // Configures the RecyclerView
        this.configureRecyclerView();

        // Updates the list of the RecyclerView
        this.updateRecyclerView(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MeetingViewModel model = ViewModelProviders.of(getActivity()).get(MeetingViewModel.class);
        mMeetings = model.getMeetings();
        mMeetings.observe(getViewLifecycleOwner(), meetings -> mAdapter.updateData(meetings));
    }

    // INTERFACE FRAGMENT VIEW *********************************************************************

    @Override
    public void updateRecyclerView(boolean isFilter) {
        // RECYCLER VIEW
//        this.mAdapter.updateData(isFilter ? this.mFragmentPresenter.getFilteredMeetings() :
//                                            this.mFragmentPresenter.getMeetings());

        MeetingViewModel model = ViewModelProviders.of(getActivity()).get(MeetingViewModel.class);
        mMeetings = model.getMeetings();
        mMeetings.observe(getViewLifecycleOwner(), meetings -> mAdapter.updateData(meetings));

//        // FILTER FAB
        this.setVisibilityOfFilterFAB(isFilter);

        // FILTER
        this.mIsFilter = isFilter;
    }

    @Override
    public void setTextById(int id, String time) {

    }

    // INTERFACE MEETING ADAPTER LISTENER (CALLBACKS OF RECYCLER VIEW) *****************************

    @Override
    public void onClickDeleteButton(int position) {
        final String message = getString(R.string.information_delete_meeting, this.mAdapter.getMeeting(position).getTopic());
        this.configureAndShowErrorMessage(message);

        this.mMeetingViewModel.deleteMeeting(this.mAdapter.getMeeting(position), this.mIsFilter);
        updateRecyclerView(this.mIsFilter);
    }

    @Override
    public void EmptyList(boolean isEmpty) {
//        this.mTextForNoData.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
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
                }
                else {
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

        MeetingViewModel model = ViewModelProviders.of(getActivity()).get(MeetingViewModel.class);
        mMeetings = model.getMeetings();
        mMeetings.observe(getViewLifecycleOwner(), meetings -> mAdapter.updateData(meetings));
    }

    // FLOATING ACTION BUTTON **********************************************************************

    /**
     * Sets the visibility of the Add {@link FloatingActionButton} thanks to the boolean in argument
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
