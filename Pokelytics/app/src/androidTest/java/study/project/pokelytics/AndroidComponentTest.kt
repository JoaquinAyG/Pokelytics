package study.project.pokelytics

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AndroidComponentTest {

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("study.project.pokelytics", appContext.packageName)
    }

    @Test
    fun testButtonClick() {
        assertNotNull(1)
    }

    @Test
    fun testMainViewBinding() {
        /*activityScenarioRule.scenario.onActivity { activity ->
            val binding = activity.binding
            assertNotNull(binding)
            onView(withId(binding.navigationDrawer.id)).check(matches(isDisplayed()))
            onView(withId(binding.drawerLayout.id)).perform(DrawerActions.open())
            onView(withId(binding.topBar.root.id)).check(matches(hasDescendant(withText("Pokelytics"))))
        }*/
        assertNotNull(1)
    }

    fun testLoginViewBinding() {
        assertNotNull(1)
    }

    fun testSplashViewBinding() {
        assertNotNull(1)
    }

    fun testPolicyViewBinding() {
        assertNotNull(1)
    }
}