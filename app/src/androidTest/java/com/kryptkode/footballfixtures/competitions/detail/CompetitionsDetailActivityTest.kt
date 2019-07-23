package com.kryptkode.footballfixtures.competitions.detail

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.BaseUiTest
import com.kryptkode.footballfixtures.app.base.fragment.BaseFragment
import com.kryptkode.footballfixtures.app.data.mock.FakeDbData
import com.kryptkode.footballfixtures.app.data.repo.TestAppRepository
import com.kryptkode.footballfixtures.app.utils.Constants
import com.kryptkode.footballfixtures.competitions.detail.fixtures.adapter.FixturesViewHolder
import com.kryptkode.footballfixtures.competitions.detail.table.adapter.TableViewHolder
import com.kryptkode.footballfixtures.competitions.detail.teams.TeamsFragment
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CompetitionsDetailActivityTest : BaseUiTest() {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(CompetitionsDetailActivity::class.java, true, false)

    @Before
    fun init() {
        activityRule.activity?.fragmentListProvider?.list?.forEach {
            IdlingRegistry.getInstance()
                .register((it as BaseFragment<*, *>?)?.provideIdlingResource())
        }
    }


    /**
     * When the activity is launched without intent extras,
     * the default title is shown
     * */
    @Test
    fun withoutIntentExtras() {
        activityRule.launchActivity(null)
        //verify the title
        val title = getString(R.string.app_name)
        onView(isAssignableFrom(Toolbar::class.java))
            .check(matches(withToolbarTitle(title)))
    }

    @Test
    fun should_Not_Navigate_On_Item_Click_In_Table_Screen() {
        val intent = Intent()
        val competition = FakeDbData.getFakeCompetition()
        intent.putExtra(Constants.EXTRAS, CompetitionsDetailActivity.getBundleExtras(competition))
        activityRule.launchActivity(intent)

        val toolbarTitle = competition.name ?: ""
        val tableTitle = getString(R.string.title_table)

        //verify that the toolbar title is expected
        onView(isAssignableFrom(Toolbar::class.java))
            .check(matches(withToolbarTitle(toolbarTitle)))

        //click on the table item the navigation drawer
        onView(onTabLayout(tableTitle)).perform(click())


        //click on an item
        onView(withRecyclerView())
            .perform(
                RecyclerViewActions.scrollToPosition<TableViewHolder>(
                    TestAppRepository.DATA_SIZE
                )
            )
            .perform(click())

        //title remains the same after clicking on an item
        onView(isAssignableFrom(Toolbar::class.java))
            .check(matches(withToolbarTitle(toolbarTitle)))

    }


    @Test
    fun should_Not_Navigate_On_Item_Click_In_Fixtures_Screen() {
        val intent = Intent()
        val competition = FakeDbData.getFakeCompetition()
        intent.putExtra(Constants.EXTRAS, CompetitionsDetailActivity.getBundleExtras(competition))
        activityRule.launchActivity(intent)

        val toolbarTitle = competition.name ?: ""
        val tableTitle = getString(R.string.title_fixtures)

        //verify that the toolbar title is expected
        onView(isAssignableFrom(Toolbar::class.java))
            .check(matches(withToolbarTitle(toolbarTitle)))

        //click on the table item the navigation drawer
        onView(onTabLayout(tableTitle)).perform(click())


        //click on an item
        onView(withRecyclerView())
            .perform(
                RecyclerViewActions.scrollToPosition<FixturesViewHolder>(
                    TestAppRepository.DATA_SIZE
                )
            )
            .perform(click())

        //title remains the same after clicking on an item
        onView(isAssignableFrom(Toolbar::class.java))
            .check(matches(withToolbarTitle(toolbarTitle)))

    }


    @Test
    fun should_Navigate_On_Item_Click_In_Teams_Screen() {
        val intent = Intent()
        val competition = FakeDbData.getFakeCompetition()
        intent.putExtra(Constants.EXTRAS, CompetitionsDetailActivity.getBundleExtras(competition))
        activityRule.launchActivity(intent)

        val toolbarTitle = competition.name ?: ""
        val tableTitle = getString(R.string.title_teams)

        //verify that the toolbar title is expected
        onView(isAssignableFrom(Toolbar::class.java))
            .check(matches(withToolbarTitle(toolbarTitle)))

        //click on the table item the navigation drawer
        onView(onTabLayout(tableTitle)).perform(click())


        //click on an item
        onView(withRecyclerView())
            .perform(
                RecyclerViewActions.scrollToPosition<FixturesViewHolder>(
                    TestAppRepository.DATA_SIZE
                )
            )
            .perform(click())

        val clickedItem =
            (activityRule.activity.fragmentListProvider.list[2] as TeamsFragment).clickedItem

        //title remains the same after clicking on an item
        onView(withId(R.id.tv_title)) //used custom title
            .check(matches(withText(clickedItem?.name ?: "")))
    }


    private fun withRecyclerView(): Matcher<View> {
        return allOf(isDisplayed(), withId(R.id.recycler_view))
    }

    private fun onTabLayout(title: String): Matcher<View> {
        return allOf(
            withText(title),
            isDescendantOfA(withId(R.id.tabLayout))
        )
    }

}