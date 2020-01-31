package com.example.maru.view;

public interface View {

    // INTERFACES ----------------------------------------------------------------------------------

    interface FragmentView {

        /**
         * Updates the {@link androidx.recyclerview.widget.RecyclerView}
         *
         * @param isFilter a boolean
         */
        void updateRecyclerView(boolean isFilter);

        /**
         * Updates the hour in hh:mm format by id
         *
         * @param id   an integer that contains the id value
         * @param time a {@link String} that contains the hour in hh:mm format
         */
        void setTextById(int id, int time);
    }
}
