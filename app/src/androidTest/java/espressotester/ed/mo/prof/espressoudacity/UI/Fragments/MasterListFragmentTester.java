package espressotester.ed.mo.prof.espressoudacity.UI.Fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.LinearLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Activities.DetailActivity;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments.MasterListFragment;
import espressotester.ed.mo.prof.espressoudacity.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

/**
 * Created by Prof-Mohamed Atef on 11/6/2018.
 */
@RunWith(AndroidJUnit4.class)
public class MasterListFragmentTester {

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
        LinearLayout LinearLayoutContainer=(LinearLayout) mACtivity.findViewById(R.id.fragment_container);
        assertNotNull(LinearLayoutContainer);
        MasterListFragment masterListFragment=new MasterListFragment();
        mACtivity.getSupportFragmentManager().beginTransaction().add(LinearLayoutContainer.getId(),masterListFragment).commitAllowingStateLoss();
        getInstrumentation().waitForIdleSync();
        // test if the fragment is launched or not
        View view=masterListFragment.getView().findViewById(R.id.recycler_view_recipes_steps);
        View view_2=masterListFragment.getView().findViewById(R.id.recycler_view_horizontal_ingredients);
        assertNotNull(view);
        assertNotNull(view_2);

    }

    @After
    public void tearDown() throws Exception {
        mACtivity=null;
    }
}