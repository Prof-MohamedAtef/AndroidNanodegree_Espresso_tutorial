package espressotester.ed.mo.prof.espressoudacity.UI.Fragments;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.LinearLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Activities.ExoPlayerActivity;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Activities.MainActivity;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments.UIIdentifierFragment;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Util;
import espressotester.ed.mo.prof.espressoudacity.R;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Prof-Mohamed Atef on 11/5/2018.
 */
@RunWith(AndroidJUnit4.class)
public class UIIdentifierFragmentTester {

    private MainActivity mACtivity=null;

    @Rule
    public ActivityTestRule<MainActivity>
            mActivityTestRule=new ActivityTestRule<MainActivity>(MainActivity.class);

    private int recipe_step_1stposition=0;
    private int recipe_step_2ndposition=1;
    private int recipe_step_3rdposition=2;
    private int recipe_step_4thposition=3;

    @Before
    public void setUp() throws Exception {
        mACtivity=mActivityTestRule.getActivity();
    }

    @Test
    public void TestLaunch(){
        LinearLayout linearLayoutContainer=(LinearLayout) mACtivity.findViewById(R.id.fragment_container);
        assertNotNull(linearLayoutContainer);
        UIIdentifierFragment uiIdentifierFragment=new UIIdentifierFragment();
        mACtivity.getSupportFragmentManager().beginTransaction().add(linearLayoutContainer.getId(),uiIdentifierFragment).commitAllowingStateLoss();
        getInstrumentation().waitForIdleSync();
        // test if the fragment is launched or not
        View view=uiIdentifierFragment.getView().findViewById(R.id.recycler_view_recipes);
        assertNotNull(view);
        // test if the adapter view first position is clickable
        onView(allOf(isDisplayed(),withId(R.id.recycler_view_recipes)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // test if recyclerview layout has similar text in recipes_layout
        ViewInteraction TextView = onView(
        allOf(withText("Nutella Pie"), isDisplayed()));
        ViewInteraction TextView2 = onView(
                allOf(withText("Brownies"), isDisplayed()));
        ViewInteraction TextVie3 = onView(
                allOf(withText("Yellow Cake"), isDisplayed()));
        ViewInteraction TextView4 = onView(
                allOf(withText("Cheesecake"), isDisplayed()));

        // test if the adapter view first position is clickable
        onView(allOf(isDisplayed(),withId(R.id.recycler_view_horizontal_ingredients)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // test if next and prev Buttons in ExoPlayerActivity or in PartitionFragment is displayed !
        ViewInteraction Button_Next = onView(allOf(withText("Next"), isDisplayed()));
        ViewInteraction Button_Pre = onView(allOf(withText("Pre"), isDisplayed()));
        if (Util.TwoPane_testing){
            // true
            for (int i=0; i<=3; i ++){
                if (i==0){
                    // test if the adapter view first position is clickable
                    onView(allOf(isDisplayed(),withId(R.id.recycler_view_recipes_steps)))
                            .perform(RecyclerViewActions.actionOnItemAtPosition(recipe_step_1stposition, click()));
                    ViewInteraction TextDescription1=onView(allOf(withText("Recipe Introduction"), isDisplayed()));
                }else if (i==1){
                    onView(allOf(isDisplayed(),withId(R.id.recycler_view_recipes_steps)))
                            .perform(RecyclerViewActions.actionOnItemAtPosition(recipe_step_2ndposition, click()));
                    ViewInteraction TextDescription2=onView(allOf(withText("1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan."), isDisplayed()));
                }else if (i==2){
                    onView(allOf(isDisplayed(),withId(R.id.recycler_view_recipes_steps)))
                            .perform(RecyclerViewActions.actionOnItemAtPosition(recipe_step_3rdposition, click()));
                    ViewInteraction TextDescription2=onView(allOf(withText("2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed."), isDisplayed()));
                }else if (i==3){
                    onView(allOf(isDisplayed(),withId(R.id.recycler_view_recipes_steps)))
                            .perform(RecyclerViewActions.actionOnItemAtPosition(recipe_step_4thposition, click()));
                    ViewInteraction TextDescription2=onView(allOf(withText("3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature."), isDisplayed()));
                }
            }
            onView(allOf(isDisplayed(),withId(R.id.recycler_view_recipes_steps)))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(recipe_step_1stposition, click()));
            ViewInteraction TextDescription1=onView(allOf(withText("Recipe Introduction"), isDisplayed()));
        }else if (!Util.TwoPane_testing){
            // false
            onView(allOf(isDisplayed(),withId(R.id.recycler_view_recipes_steps)))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(recipe_step_1stposition, click()));
            ViewInteraction TextDescription1=onView(allOf(withText("Recipe Introduction"), isDisplayed()));
            // is Video Container displayed !
            onView(withId(R.id.Video_player_container)).check(matches(isDisplayed()));
            TestVideosControlsWhen_recycler_view_recipes_stepsisSelected();
        }
    }


    private void TestVideosControlsWhen_recycler_view_recipes_stepsisSelected() {
        // is exo_pause focusable
        onView(withId(R.id.exo_pause)).check(matches(isFocusable()));
        ViewInteraction pause = onView(
                allOf(withId(R.id.exo_pause), withContentDescription("Pause"), isFocusable()));
        // is exo_play focusable
        onView(withId(R.id.exo_play)).check(matches(isFocusable()));
        ViewInteraction play = onView(
                allOf(withId(R.id.exo_play), withContentDescription("Play"),isFocusable()));
        ViewInteraction forward = onView(withId(R.id.exo_ffwd));
        ViewInteraction playPosition = onView(withId(R.id.exo_position));
        ViewInteraction rewind = onView(withId(R.id.exo_rew));
        ViewInteraction back = onView(withId(R.id.exo_prev));
    }

    @After
    public void tearDown() throws Exception {
        mACtivity=null;
    }
}