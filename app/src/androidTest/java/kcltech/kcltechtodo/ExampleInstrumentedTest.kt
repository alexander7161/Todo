package kcltech.kcltechtodo

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import org.junit.Rule




/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    @Throws(Exception::class)
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("kcltech.kcltechtodo", appContext.packageName)
    }

    /**
     * [ActivityTestRule] is a JUnit [@Rule][Rule] to launch your activity under test.
     *
     *
     *
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    @JvmField
    val activity = ActivityTestRule<TListActivity>(TListActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun clickAddNoteButton_opensAddNoteUi() {
        // Click on the add note button
        onView(withId(R.id.fab)).perform(click())

        // Check if the add note screen is displayed
        onView(withId(R.id.titleInput)).check(matches(isDisplayed()))
    }
}
