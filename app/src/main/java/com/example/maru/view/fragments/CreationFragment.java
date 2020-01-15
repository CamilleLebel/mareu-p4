package com.example.maru.view.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
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
import com.example.maru.models.Member;
import com.example.maru.utils.ShowMessage;
import com.example.maru.view.adapters.MemberAdapter;
import com.example.maru.view.base.BaseFragment;
import com.example.maru.view.dialogFragment.TimePickerFragment;
import com.example.maru.viewModels.MeetingViewModel;
import com.example.maru.viewModels.MemberViewModel;
import com.example.maru.viewModels.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CreationFragment extends BaseFragment implements MemberAdapter.MemberAdapterListener {

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

    public final int ID_SEARCH_HOUR = 1;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public CreationFragment() { }

    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_creation;
    }

    @Override
    protected void configureDesign() {
        this.configureEditTextFromTextInputLayout();
        this.configureRoomSpinner();
        this.configureRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        mSharedViewModel.getTextTime().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mHours.setText(s);
            }
        });
        mMemberViewModel = ViewModelProviders.of(getActivity()).get(MemberViewModel.class);
        mMemberViewModel.getMembers().observe(getViewLifecycleOwner(), new Observer<List<Member>>() {
            @Override
            public void onChanged(List<Member> members) {
                mAdapter.updateData(members);
            }
        });
    }

    // INTERFACE FRAGMENT VIEW *********************************************************************

    @Override
    public void setTextById(int id, String time) {
        if (id == ID_SEARCH_HOUR) {

            this.mHours.setText(time);
            this.mSharedViewModel.setTextTime(time);
        }
    }

    // INTERFACE MEMBER ADAPTER LISTENER (CALLBACKS OF RECYCLER VIEW) ******************************

    @Override
    public void onClickCheckBox(int position) {


//        this.mMemberViewModel.AddOrDeleteSelectedMember(this.mAdapter.getMember(position));

        this.mMemberViewModel.addOrDeleteSelectedMember(this.mAdapter.getMember(position));


    }

    // ACTIONS *************************************************************************************

    @OnClick(R.id.fragment_creation_btn_hour)
    public void onHourButtonClicked() {
        TimePickerFragment.newInstance(ID_SEARCH_HOUR).show(getActivity().getSupportFragmentManager(), "TIME PICKER");
    }

    @OnClick(R.id.fragment_creation_fab)
    public void onFABClicked() {

        // TEXT INPUT EDIT TEXT: Empty
        if (this.mTextInputTopic.getEditText().getText().toString().equals("")) {
            this.mTextInputTopic.setError(getString(R.string.error_text_input_layout));
            this.configureAndShowErrorMessage(getString(R.string.error_topic_meeting_creation));
        }
        // RECYCLER VIEW: No selected member
        else if (this.mMemberViewModel.getSelectedMembers().size() == 0) {
            this.configureAndShowErrorMessage(getString(R.string.error_member_meeting_creation));
        }
        else {
            this.configureAndShowAlertDialog();
        }
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
                                                            this.mMeetingViewModel.getRoomsName());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner
        this.mRoomSpinner.setAdapter(adapter);
    }

    // RECYCLER VIEW *******************************************************************************

    private void configureRecyclerView() {
        this.mAdapter = new MemberAdapter(this);

        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MemberViewModel model = ViewModelProviders.of(getActivity()).get(MemberViewModel.class);
        LiveData<List<Member>> mMembers = model.getMembers();
        mMembers.observe(getViewLifecycleOwner(), new Observer<List<Member>>() {
            @Override
            public void onChanged(List<Member> members) {
                mAdapter.updateData(members);
            }
        });


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

    // ALERT DIALOG ********************************************************************************

    private void configureAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getString(R.string.creation_of_meeting))
               .setMessage("Are you sure to create this meeting? " + this.mTextInputTopic.getEditText().getText().toString())
               .setPositiveButton("Yes",
                       (dialog, which) -> {
                            final String topic = this.mMeetingViewModel.addMeeting(this.mTextInputTopic.getEditText().getText().toString(),
                                                                                    this.mHours.getText().toString(),
                                                                                    (String) this.mRoomSpinner.getSelectedItem(),
                                                                                    this.mMemberViewModel.getSelectedMembersToString());
                            this.mCallback.onClickFromFragment(topic);

                            mMemberViewModel.deleteAllSelectedMember();
                            mMemberViewModel.resetMembers();
               })
               .setNegativeButton("No",
                       (dialog, which) -> {

               });
        builder.create().show();
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
