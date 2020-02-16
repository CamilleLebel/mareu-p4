package com.example.maru.view.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.maru.view.View.FragmentView;
import com.example.maru.viewModels.MeetingViewModel;
import com.example.maru.viewModels.MemberViewModel;
import com.example.maru.viewModels.SharedViewModel;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements FragmentView {

    // INTERFACES ----------------------------------------------------------------------------------

    public interface FragmentListener {

        void showMessageFromFragment(String message);

        void onClickFromFragment(String message);

    }

    // FIELDS --------------------------------------------------------------------------------------

    protected FragmentListener mCallback;
    protected MeetingViewModel mMeetingViewModel;
    protected MemberViewModel mMemberViewModel;
    protected SharedViewModel mSharedViewModel;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public BaseFragment() {
    }

    // METHODS -------------------------------------------------------------------------------------

    protected abstract int getFragmentLayout();

    protected abstract void configureDesign();

    // FRAGMENT ************************************************************************************

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Configures the callback to the parent activity
        this.configureCallbackToParentActivity(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(this.getFragmentLayout(), container, false);

        ButterKnife.bind(this, view);


        this.configureDesign();

        return view;
    }

    // INTERFACE FRAGMENT VIEW *********************************************************************

    @Override
    public void updateRecyclerView(boolean isFilter) {
    }

    @Override
    public void setTextById(int id, int time) {
    }

    // CALLBACK OF ACTIVITY ************************************************************************

    /**
     * Configures {@link FragmentListener}(callbacks) to the parent activity
     *
     * @param context a {@link Context} which contains the {@link Fragment}
     */
    private void configureCallbackToParentActivity(Context context) {
        // Initializes the callback field
        try {
            this.mCallback = (FragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement FragmentListener");
        }
    }
}
