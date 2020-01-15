package com.example.maru.view.dialogFragment;

import android.widget.TimePicker;

public interface TimePickerFragmentListener {

    /**
     * Allows to retrieve the id of the {@link TimePicker}
     * @param id an integer that allows to identifier the {@link TimePicker}
     * @param view a {@link TimePicker}
     * @param hourOfDay an integer that contains the hour value
     * @param minute an integer that contains the minute value
     */
    void onTimeSet(int id, TimePicker view, int hourOfDay, int minute);


}
