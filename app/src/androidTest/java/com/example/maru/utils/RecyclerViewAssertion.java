package com.example.maru.utils;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static org.junit.Assert.assertThat;


public class RecyclerViewAssertion implements ViewAssertion {

    // FIELDS --------------------------------------------------------------------------------------

    private final Matcher<Integer> mMatcher;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     * @param matcher a {@link Matcher} of {@link Integer}
     */
    private RecyclerViewAssertion(Matcher<Integer> matcher) {
        this.mMatcher = matcher;
    }

    // METHODS -------------------------------------------------------------------------------------

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        assertThat(adapter.getItemCount(), this.mMatcher);
    }

    // WITH ITEM COUNT *****************************************************************************

    /**
     * returns a {@link RecyclerViewAssertion}
     * @param expectedCount an integer that contains the expected value
     * @return a {@link RecyclerViewAssertion}
     */
    public static RecyclerViewAssertion withItemCount(final int expectedCount) {
        return withItemCount(Matchers.is(expectedCount));
    }

    /**
     * returns a {@link RecyclerViewAssertion}
     * @param matcher a {@link Matcher} of {@link Integer}
     * @return a {@link RecyclerViewAssertion}
     */
    private static RecyclerViewAssertion withItemCount(Matcher<Integer> matcher) {
        return new RecyclerViewAssertion(matcher);
    }
}
