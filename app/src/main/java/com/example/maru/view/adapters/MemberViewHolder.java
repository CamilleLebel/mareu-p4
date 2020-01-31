package com.example.maru.view.adapters;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.maru.R;
import com.example.maru.models.Member;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberViewHolder extends RecyclerView.ViewHolder {

    // FIELDS --------------------------------------------------------------------------------------

    @BindView(R.id.item_member_iv_image)
    ImageView mAvatarUrl;
    @BindView(R.id.item_member_tv_name)
    TextView mName;
    @BindView(R.id.item_member_tv_email)
    TextView mEmail;
    @BindView(R.id.item_member_cb_check)
    CheckBox mCheckBox;

    private WeakReference<MemberAdapter.MemberAdapterListener> mListenerWeakReference;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    public MemberViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Returns the layout value
     *
     * @return an integer that contains the layout value
     */
    public static int getLayout() {
        return R.layout.item_member;
    }

    // ACTIONS *************************************************************************************

    @OnClick(R.id.item_member_cb_check)
    public void onCheckBoxClicked() {
        MemberAdapter.MemberAdapterListener callback = this.mListenerWeakReference.get();

        if (callback != null) {
            callback.onClickCheckBox(getAdapterPosition());
        }
    }

    // UI ******************************************************************************************

    public void updateMember(Member member, MemberAdapter.MemberAdapterListener callback) {
        // IMAGE
        Glide.with(itemView.getContext())
                .load(member.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(mAvatarUrl);

        // FIRST AND LAST NAMES
        final String fullName = member.getFirstName() + " " + member.getLastName();
        this.mName.setText(fullName);

        // EMAIL
        this.mEmail.setText(member.getEmail());

        // LISTENER
        this.mListenerWeakReference = new WeakReference<>(callback);
    }

}
