package com.kryptkode.footballfixtures.competitions

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingRegistry
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.BaseUiTest
import com.kryptkode.footballfixtures.app.data.repo.TestAppRepository.Companion.DATA_SIZE
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class CompetitionsFragmentTest : BaseUiTest() {
    private lateinit var scenario: FragmentScenario<CompetitionsFragment>
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
            assertThat(recyclerView?.adapter, CoreMatchers.notNullValue())
            assertThat(recyclerView?.adapter?.itemCount, CoreMatchers.`is`(DATA_SIZE))
        }
    }
}