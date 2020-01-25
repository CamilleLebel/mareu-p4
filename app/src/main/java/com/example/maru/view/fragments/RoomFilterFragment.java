package com.example.maru.view.fragments;

import android.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.lifecycle.ViewModelProviders;

import com.example.maru.R;
import com.example.maru.di.DI;
import com.example.maru.di.ViewModelFactory;
import com.example.maru.view.base.BaseFragment;
import com.example.maru.view.dialogFragment.AddRoomFilterFragment;
import com.example.maru.viewModels.MeetingViewModel;
import com.example.maru.viewModels.MemberViewModel;
import com.example.maru.viewModels.RoomViewModel;
import com.example.maru.viewModels.SharedViewModel;

import butterknife.BindView;
import butterknife.OnClick;

public class RoomFilterFragment extends BaseFragment implements AddRoomFilterFragment.AddRoomFilterDialogListener {

    // FIELDS --------------------------------------------------------------------------------------

    @BindView(R.id.fragment_filter_room_spinner)
    Spinner mRoomSpinner;
    @BindView(R.id.fragment_filter_room_button)
    Button mFilterButton;

    private RoomViewModel mRoomViewModel;
    private MeetingViewModel mMeetingViewModel;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     */
    public RoomFilterFragment() {}

    // METHODS -------------------------------------------------------------------------------------

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_filter_room;
    }

    @Override
    protected void configureDesign() {
        this.configureViewModel();
        this.configureRoomSpinner();
    }

    // ACTIONS *************************************************************************************

    @OnClick(R.id.fragment_filter_room_button)
    public void onViewClicked(View view) {
        AddRoomFilterFragment.newInstance(getCurrentRoomOfSpinner()).show(getActivity().getSupportFragmentManager(), "filter_creation");
//        this.configureAndShowAlertDialog();
    }

    // VIEWMODEL ***********************************************************************************

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = DI.provideViewModelFactory(getActivity());

        this.mMeetingViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MeetingViewModel.class);
        this.mRoomViewModel = ViewModelProviders.of(this, mViewModelFactory).get(RoomViewModel.class);
    }
    // SPINNERS ************************************************************************************

    /**
     * Configures the room spinner
     */
    private void configureRoomSpinner() {
        // Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                this.mRoomViewModel.getRoomsName());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner
        this.mRoomSpinner.setAdapter(adapter);
    }
    /**
     * Returns the current room of {@link Spinner}
     * @return a {@link String} that contains the current room of {@link Spinner}
     */
    private String getCurrentRoomOfSpinner() {
        return (String) this.mRoomSpinner.getSelectedItem();
    }
    // INSTANCES ***********************************************************************************

    /**
     * Returns a {@link RoomFilterFragment}
     * @return a {@link RoomFilterFragment}
     */
    public static RoomFilterFragment newInstance() {
        return new RoomFilterFragment();
    }

    // ALERT DIALOG ********************************************************************************

    /**
     * Configures and show the {@link AlertDialog}
     */
    private void configureAndShowAlertDialog() {
        // Creates Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Modifies the title
        builder.setTitle(getString(R.string.creation_of_room_filter))
                .setMessage(getString(R.string.question_for_creation_of_room_filter,
                        this.getCurrentRoomOfSpinner()))
                .setPositiveButton(getString(R.string.yes),
                        (dialog, which) -> {
                            this.mMeetingViewModel.filterPerRoom(this.getCurrentRoomOfSpinner());

                            this.mCallback.onClickFromFragment(null);})
                .setNegativeButton(getString(R.string.no),
                        (dialog, which) -> {});

        // Creates and shows the AlertDialog widget
        builder.create().show();
    }

    @Override
    public void onYesClicked(String roomName) {
        this.mMeetingViewModel.filterPerRoom(this.getCurrentRoomOfSpinner());
        this.mCallback.onClickFromFragment(null);
    }
}
