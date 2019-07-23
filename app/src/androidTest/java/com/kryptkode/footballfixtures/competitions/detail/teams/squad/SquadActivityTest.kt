package com.kryptkode.footballfixtures.competitions.detail.teams.squad

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.BaseUiTest
import com.kryptkode.footballfixtures.app.data.mock.FakeDbData
import com.kryptkode.footballfixtures.app.utils.Constants
import org.junit.Rule
import org.junit.Test

class SquadActivityTest : BaseUiTest() {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(SquadActivity::class.java, true, false)

    /**
     * When the activity is launched without intent extras,
     * the default title is shown
     * */
    @Test
    fun withoutIntentExtras() {
        activityRule.launchActivity(null)
        //verify the title
        val title = getString(R.string.app_name)
        onView(withId(R.id.tv_title)) //used custom title
            .check(matches(withText(title)))
    }

    @Test
    fun withIntentExtras() {
        val intent = Intent()
        val team = FakeDbData.getFakeTeam()
        intent.putExtra(Constants.EXTRAS, SquadActivity.getBundleExtra(team))
        activityRule.launchActivity(intent)

        //verify the title
        onView(withId(R.id.tv_title)) //used custom title
            .check(matches(withText(team.name)))

    }
}