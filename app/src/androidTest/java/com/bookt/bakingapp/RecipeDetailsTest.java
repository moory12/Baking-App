package com.bookt.bakingapp;



import android.content.Intent;
import android.util.Log;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;

import com.bookt.bakingapp.Classes.Recipe;




import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;


@RunWith(AndroidJUnit4.class)
public class RecipeDetailsTest  {

    @Rule public ActivityTestRule<RecipeDetailActivity> recipeDetailActivityActivityTestRule = new ActivityTestRule<>(RecipeDetailActivity.class,false,false);
    @Rule public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    ArrayList<Recipe> arrayList;
    @Before
    public void lunchMainActivty() throws InterruptedException {
        Thread.sleep(1000);
        arrayList = mainActivityActivityTestRule.getActivity().recipes;
    }



    @Test

    public void recipeTestActivity(){

        for(int i=0;i<arrayList.size();i++){

            Intent intent = new Intent();
            intent.putExtra("ID",i);
            intent.putParcelableArrayListExtra("RECIPE",arrayList);
            recipeDetailActivityActivityTestRule.launchActivity(intent);


            if (recipeDetailActivityActivityTestRule.getActivity().tablet) {
                for(int j=0;j<arrayList.get(i).getSteps().size();j++) {


                    onData(anything()).inAdapterView(withId(R.id.steps_fragment)).atPosition(j).perform(click());
                    onView(withId(R.id.instruction_fragment)).check(matches(isDisplayed()));
                    onView(withId(R.id.instruction_textview)).check(matches(withText(arrayList.get(i).getSteps().get(j).getDescription())));
                }
            }
            else{
            for(int j=0;j<arrayList.get(i).getSteps().size();j++){
                onView(allOf(withId(R.id.recipe_steps_recycler),isDisplayed())).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText((j+1)+"- "+arrayList.get(i).getSteps().get(j).getShortDescription()+"")),click()));
                    onView(withId(R.id.instruction_tv)).check(matches((withText(arrayList.get(i).getSteps().get(j).getDescription()))));
                    Espresso.pressBack();
            }

            }

            recipeDetailActivityActivityTestRule.finishActivity();

        }


    }
}
