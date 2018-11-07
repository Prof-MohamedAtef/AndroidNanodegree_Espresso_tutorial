package espressotester.ed.mo.prof.espressoudacity.UI.Fragments;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.FrameLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Activities.DetailActivity;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments.PartitionFragment;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Util;
import espressotester.ed.mo.prof.espressoudacity.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.*;

/**
 * Created by Prof-Mohamed Atef on 11/6/2018.
 */
@RunWith(AndroidJUnit4.class)
public class PartitionFragmentTester {

    private DetailActivity mACtivity=null;


    @Rule
    public ActivityTestRule<DetailActivity>
            mActivityTestRule=new ActivityTestRule<DetailActivity>(DetailActivity.class);


    @Before
    public void setUp() throws Exception {
        mACtivity=mActivityTestRule.getActivity();
    }

    @Test
    public void TestLaunch(){
        // just valid for testing on more than 600 dp screens
        if (Util.TwoPane_testing){
            FrameLayout frameLayoutContainer=(FrameLayout) mACtivity.findViewById(R.id.StepInstruction_container);
            assertNotNull(frameLayoutContainer);
            PartitionFragment partitionFragment=new PartitionFragment();
            mACtivity.getSupportFragmentManager().beginTransaction().add(frameLayoutContainer.getId(),partitionFragment).commitAllowingStateLoss();
            getInstrumentation().waitForIdleSync();
            // test if the fragment is launched or not
            View view=partitionFragment.getView().findViewById(R.id.description_txt);
            assertNotNull(view);

            // test if next and prev Buttons in PartitionFragment is displayed !
            ViewInteraction Button_Next = onView(allOf(withText("Next"), isDisplayed()));
            ViewInteraction Button_Pre = onView(allOf(withText("Pre"), isDisplayed()));
        }
    }


    @After
    public void tearDown() throws Exception {
        mACtivity=null;
    }

}