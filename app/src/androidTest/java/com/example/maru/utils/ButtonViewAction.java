package com.example.maru.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;


public class ButtonViewAction implements ViewAction {

    // FIELDS --------------------------------------------------------------------------------------

    private final int mId;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     * @param id an integer that contains the id value
     */
    public ButtonViewAction(int id) {
        this.mId = id;
    }

    // METHODS -------------------------------------------------------------------------------------

    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View button = view.findViewById(this.mId);
        button.performClick();
    }
}
