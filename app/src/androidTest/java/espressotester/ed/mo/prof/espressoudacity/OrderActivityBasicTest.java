package espressotester.ed.mo.prof.espressoudacity;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Prof-Mohamed Atef on 11/4/2018.
 */
@RunWith(AndroidJUnit4.class)
public class OrderActivityBasicTest {

    @Rule
    public ActivityTestRule<OrderActivity> mActivityTestRule
            = new ActivityTestRule<>(OrderActivity.class);

    @Test
    public void clickIncrementButton_ChangesQuantityAndCost() {
        // 1. Find the view
        // 2. perform Action on the View
        // 3. check if the view does what you expect
        onView((ViewMatchers.withId(R.id.increment_button))).perform(click());
        onView(ViewMatchers.withId(R.id.quantity_text_view)).check(matches(withText("1")));
        onView(ViewMatchers.withId(R.id.cost_text_view)).check(matches(withText("$5.00")));
    }
}