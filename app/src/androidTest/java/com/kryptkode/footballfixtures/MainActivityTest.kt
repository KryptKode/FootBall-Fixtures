package com.kryptkode.footballfixtures

import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.kryptkode.footballfixtures.app.base.BaseUiTest
import com.kryptkode.footballfixtures.app.data.repo.TestAppRepository
import com.kryptkode.footballfixtures.app.utils.BottomNavigationViewActions
import com.kryptkode.footballfixtures.todaysfixtures.adapter.TodaysFixturesViewHolder
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest : BaseUiTest() {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init() {
        val fixturesIdlingResource = activityRule.activity
            .fragments.todaysFixturesFragment.provideIdlingResource()
        val competitionsIdlingResource = activityRule.activity
            .fragments.competitionsFragment.provideIdlingResource()

        IdlingRegistry.getInstance().register(fixturesIdlingResource, competitionsIdlingResource)
    }

    @Test
    fun toolbarTitle() {

        var title = getString(R.string.title_todays_fixtures)

        //check the default title
        onView(isAssignableFrom(Toolbar::class.java))
            .check(matches(withToolbarTitle(title)))

        //click another item in bottom navigation
        onView(withId(R.id.nav_view))
            .perform(BottomNavigationViewActions.navigateTo(R.id.navigation_competitions))

        //verify the title
        title = getString(R.string.title_competitions)
        onView(isAssignableFrom(Toolbar::class.java))
            .check(matches(withToolbarTitle(title)))
    }

    @Test
    fun clickFixturesItemDoesNotNavigate() {
        val title = getString(R.string.title_todays_fixtures)

        //click  on the item on the bottom navigation
        onView(withId(R.id.nav_view))
            .perform(BottomNavigationViewActions.navigateTo(R.id.navigation_fixtures))


        //check the default title
        onView(isAssignableFrom(Toolbar::class.java))
            .check(matches(withToolbarTitle(title)))

        //click on an item
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions.scrollToPosition<TodaysFixturesViewHolder>(
                    TestAppRepository.DATA_SIZE
                )
            )
            .perform(ViewActions.click())

        //title remains the same after clicking on an item
        onView(isAssignableFrom(Toolbar::class.java))
            .check(matches(withToolbarTitle(title)))
    }


    @Test
    fun clickCompetitionsItemNavigateNavigatesToTheDetail() {
        val title = getString(R.string.title_competitions)

        //click on the item on the bottom navigation
        onView(withId(R.id.nav_view))
            .perform(BottomNavigationViewActions.navigateTo(R.id.navigation_competitions))

        //check the default title
        onView(isAssignableFrom(Toolbar::class.java))
            .check(matches(withToolbarTitle(title)))

        //click on an item
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions.scrollToPosition<TodaysFixturesViewHolder>(
                    TestAppRepository.DATA_SIZE
                )
            )
            .perform(ViewActions.click())

        val clickedItem = activityRule.activity.fragments.competitionsFragment.clickItem

        //title remains the same after clicking on an item
        onView(isAssignableFrom(Toolbar::class.java))
            .check(matches(withToolbarTitle(clickedItem?.name ?: "")))
    }

}