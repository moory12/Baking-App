package com.bookt.bakingapp;





import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.bookt.bakingapp.Classes.Recipe;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.internal.util.Checks.checkNotNull;
import androidx.test.espresso.contrib.RecyclerViewActions;
import static org.hamcrest.core.AllOf.allOf;



import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

@RunWith(JUnit4.class)
public class MainActivityTest {

    @Rule public  ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Rule public  ActivityTestRule<RecipeDetailActivity> recipeDetailActivityActivityTestRule = new ActivityTestRule<>(RecipeDetailActivity.class,true,false);

    @Test
    public void RecyclerViewTest() throws InterruptedException {


        Thread.sleep(1000);
        RecyclerView recyclerView = activityActivityTestRule.getActivity().findViewById(R.id.recipe_recycler_view);

        ArrayList<Recipe> arrayList = activityActivityTestRule.getActivity().recipes;

        int x = recyclerView.getAdapter().getItemCount();
        for (int i = 0; i < x; i++) {


            onView(allOf(withId(R.id.recipe_recycler_view),isDisplayed())).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(arrayList.get(i).getName())),click()));
            Intents.init();



            onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText(arrayList.get(i).getName()))));


            Espresso.pressBack();

        }
    }



    //copied from https://stackoverflow.com/a/34795431
    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

}




