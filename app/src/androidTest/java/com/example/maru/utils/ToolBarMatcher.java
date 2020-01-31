package com.example.maru.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public abstract class ToolBarMatcher {

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Returns a {@link Matcher} of {@link View}
     * @param parentMatcher a {@link Matcher} of {@link View}
     * @param position an integer that contains the position value
     * @return a {@link Matcher} of {@link View}
     */
    public static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ViewParent parent = item.getParent();

                return parent instanceof ViewGroup &&
                       parentMatcher.matches(parent) &&
                       item.equals(((ViewGroup) parent).getChildAt(position));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }
        };
    }
}
