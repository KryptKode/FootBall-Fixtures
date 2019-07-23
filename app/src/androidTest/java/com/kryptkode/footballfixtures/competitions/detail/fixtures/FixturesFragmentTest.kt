package com.kryptkode.footballfixtures.competitions.detail.fixtures

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingRegistry
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.BaseUiTest
import com.kryptkode.footballfixtures.app.data.repo.TestAppRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class FixturesFragmentTest : BaseUiTest() {
    private lateinit var scenario: FragmentScenario<FixturesFragment>
    @Before
    fun init() {
        scenario = launchFragment(
            themeResId = R.style.AppTheme_NoActionBar
        )
        scenario.onFragment {
            val idlingResource = it.provideIdlingResource()
            IdlingRegistry.getInstance().register(idlingResource)
        }
    }

    @Test
    fun showSomeResults() {
        scenario.onFragment {
            val recyclerView = it.view?.findViewById<RecyclerView>(R.id.recycler_view)
            MatcherAssert.assertThat(recyclerView?.adapter, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(
                recyclerView?.adapter?.itemCount,
                CoreMatchers.`is`(TestAppRepository.DATA_SIZE)
            )
        }
    }
}