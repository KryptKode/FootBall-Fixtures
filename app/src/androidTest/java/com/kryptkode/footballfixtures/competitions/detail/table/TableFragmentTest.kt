package com.kryptkode.footballfixtures.competitions.detail.table

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingRegistry
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.BaseUiTest
import com.kryptkode.footballfixtures.app.data.repo.TestAppRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class TableFragmentTest : BaseUiTest() {
    private lateinit var scenario: FragmentScenario<TableFragment>
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
            assertThat(recyclerView?.adapter, notNullValue())
            assertThat(
                recyclerView?.adapter?.itemCount,
                `is`(TestAppRepository.DATA_SIZE)
            )
        }
    }
}