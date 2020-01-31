package com.example.maru.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.di.DI;
import com.example.maru.di.ViewModelFactory;
import com.example.maru.models.Meeting;
import com.example.maru.models.Member;
import com.example.maru.utils.ShowMessage;
import com.example.maru.utils.TimeTools;
import com.example.maru.view.adapters.MemberAdapter;
import com.example.maru.view.base.BaseFragment;
import com.example.maru.view.dialogFragment.AddMeetingFragment;
import com.example.maru.view.dialogFragment.TimePickerFragment;
import com.example.maru.viewModels.MeetingViewModel;
import com.example.maru.viewModels.MemberViewModel;
import com.example.maru.viewModels.RoomViewModel;
import com.example.maru.viewModels.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreationFragment extends BaseFragment implements MemberAdapter.MemberAdapterListener, AddMeetingFragment.AddMeetingDialogListener {

    // FIELDS --------------------------------------------------------------------------------------

    @BindView(R.id.fragment_creation_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.fragment_creation_text_input_layout)
    TextInputLayout mTextInputTopic;
    @BindView(R.id.fragment_creation_btn_hour)
    Button mHours;
    @BindView(R.id.fragment_creation_spinner_room)
    Spinner mRoomSpinner;
    @BindView(R.id.fragment_creator_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_creation_fab)
    FloatingActionButton mFab;

    private MemberAdapter mAdapter;

    private SharedViewModel mSharedViewModel;
    private RoomViewModel mRoomViewModel;


    public int mHourInSecond;
    public final int ID_SEARCH_HOUR = 1;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public CreationFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_creation;
    }

    @Override
    protected void configureDesign() {
        this.configureViewModel();
        this.configureEditTextFromTextInputLayout();
        this.configureRoomSpinner();
        this.configureRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Observe the hour button text

        mSharedViewModel.getTimeLiveData().observe(getViewLifecycleOwner(), integer -> {
            mHourInSecond = integer;
            String timeString = TimeTools.convertSecondToString(mHourInSecond);
            mHours.setText(timeString);
        });
    }

    // INTERFACE FRAGMENT VIEW *********************************************************************

    @Override
    public void setTextById(int id, int time) {
        if (id == ID_SEARCH_HOUR) {

            mHourInSecond = time;
            this.mSharedViewModel.setTimeLiveData(time);
            //convert second to hh:mm
            String timeString = TimeTools.convertSecondToString(mHourInSecond);
            this.mHours.setText(timeString);
        }
    }

    // INTERFACE MEMBER ADAPTER LISTENER (CALLBACKS OF RECYCLER VIEW) ******************************

    @Override
    public void onClickCheckBox(int position) {
        Member member = mAdapter.getMember(position);
        LiveData<List<Member>> selectedMembers = mMemberViewModel.getSelectedLDMembers();
        final Observer<List<Member>> selectedMembersObserver = members -> {
            if (member.isSelected()) {
                mMemberViewModel.deleteFromSelectedMembers(member);
                selectedMembers.removeObservers(getViewLifecycleOwner());
            } else {
                mMemberViewModel.addToSelectedMembers(member);
            }
        };
        selectedMembers.observe(getViewLifecycleOwner(), selectedMembersObserver);
    }

    // INTERFACE FRAGMENT DIALOG *******************************************************************

    @Override
    public void onYesAddClicked() {

        final String topic = this.mMeetingViewModel.addMeeting(
                this.mTextInputTopic.getEditText().getText().toString(),
                this.mHourInSecond,
                (String) this.mRoomSpinner.getSelectedItem(),
                this.mMemberViewModel.getSelectedMembersToString());
        this.mCallback.onClickFromFragment(topic);

        //RESET THE LIST OF SELECTED MEMBERS AND TIME BUTTON VIEW

        LiveData<List<Member>> selectedMembers = mMemberViewModel.getSelectedLDMembers();
        selectedMembers.removeObservers(getViewLifecycleOwner());
        mMemberViewModel.deleteAllSelectedMember();

        mAdapter.notifyDataSetChanged();
    }

    // ACTIONS *************************************************************************************

    @OnClick(R.id.fragment_creation_btn_hour)
    public void onHourButtonClicked() {
        TimePickerFragment.newInstance(ID_SEARCH_HOUR).show(getActivity().getSupportFragmentManager(), "TIME PICKER");
    }

    @OnClick(R.id.fragment_creation_fab)
    public void onFABClicked() {

        if (checkForCreateMeeting()) {
            Meeting meeting = new Meeting(mMeetingViewModel.getMeetings().getValue().size() + 1,
                    mTextInputTopic.getEditText().getText().toString(),
                    mHourInSecond,
                    (String) mRoomSpinner.getSelectedItem(),
                    mMemberViewModel.getSelectedMembersToString());

            AddMeetingFragment.newInstance(meeting).show(getActivity().getSupportFragmentManager(), "ADD MEETING");
        }
    }

    private boolean checkForCreateMeeting() {
        List<Member> selectedMembers = this.mMemberViewModel.getSelectedLDMembers().getValue();

        int id = mMeetingViewModel.getMeetings().getValue().size() + 1;
        String topic = this.mTextInputTopic.getEditText().getText().toString();
        int hour = mHourInSecond;
        String room = mRoomSpinner.getSelectedItem().toString();
        String selectedMembersString = mMemberViewModel.getSelectedLDMembers().toString();
        Meeting meetingToAdd = new Meeting(id, topic, hour, room, selectedMembersString);

        // TEXT INPUT EDIT TEXT: Empty
        if (topic.equals("")) {
            this.mTextInputTopic.setError(getString(R.string.error_text_input_layout));
            this.configureAndShowErrorMessage(getString(R.string.error_topic_meeting_creation));
            return false;
        }
        // RECYCLER VIEW: No selected member
        else if (selectedMembers == null || selectedMembers.size() == 0 || mMemberViewModel.getSelectedLDMembers() == null) {
            this.configureAndShowErrorMessage(getString(R.string.error_member_meeting_creation));
            return false;
        }
        for (Meeting meeting : mMeetingViewModel.getMeetings().getValue()) {
            if (meeting.getRoom().equals(meetingToAdd.getRoom())) {
                if (meeting.getHour() == meetingToAdd.getHour()) {
                    this.configureAndShowErrorMessage("Error: Meeting in the same room and at the same hour already exist");
                    return false;
                }
                // Meeting in the same room exist AND will we be not over
                else if ((meeting.getHour() < meetingToAdd.getHour() && meetingToAdd.getHour() < meeting.getHour() + 2700)) {
                    this.configureAndShowErrorMessage("Error: Room is already taken. A meeting usually lasts 45 minutes");
                    return false;
                }
            }
        }
        return true;
    }

    // INSTANCES ***********************************************************************************

    public static CreationFragment newInstance() {
        return new CreationFragment();
    }

    // SPINNERS ************************************************************************************

    private void configureRoomSpinner() {
        // Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                this.mRoomViewModel.getRoomsName());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner
        this.mRoomSpinner.setAdapter(adapter);
    }

    // RECYCLER VIEW *******************************************************************************

    private void configureRecyclerView() {
        this.mAdapter = new MemberAdapter(this);

        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LiveData<List<Member>> mMembers = mMemberViewModel.getMembers();
        mMembers.observe(getViewLifecycleOwner(), members -> mAdapter.updateData(members));
    }

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = DI.provideViewModelFactory(getActivity());

        this.mMemberViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MemberViewModel.class);
        this.mRoomViewModel = ViewModelProviders.of(this, mViewModelFactory).get(RoomViewModel.class);
        this.mSharedViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SharedViewModel.class);
        if (mMeetingViewModel == null) {
            this.mMeetingViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(MeetingViewModel.class);
        }

    }

    // TEXT INPUT EDIT TEXT ************************************************************************

    private void configureEditTextFromTextInputLayout() {
        this.mTextInputTopic.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextInputTopic.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        this.mTextInputTopic.setFocusableInTouchMode(true);
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
