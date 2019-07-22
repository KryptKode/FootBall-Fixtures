package com.kryptkode.footballfixtures

import com.kryptkode.footballfixtures.app.base.BaseTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test

class MainViewModelTest : BaseTest(){

    private  val viewModel: MainViewModel = MainViewModel()
    @Test
    fun `press back once shows prompt but does not exit app`() {
        viewModel.handleOnBackPressed()
        assertNull(viewModel.exit.value)
        assertThat(viewModel.showExitPrompt.value, `is`(Unit))
    }


    @Test
    fun `press back twice shows prompt and exits the app`() {
        viewModel.handleOnBackPressed()
        viewModel.handleOnBackPressed()
        assertThat(viewModel.exit.value, `is`(Unit))
        assertThat(viewModel.showExitPrompt.value, `is`(Unit))
    }


    @Test
    fun `should navigate when a navigation item is pressed`() {
        val itemId = R.id.navigation_competitions
        viewModel.handleNavItemSelected(itemId)
        assertThat(viewModel.navigate.value, `is`(itemId))
    }

    @Test
    fun `should navigate home when home navigation item is pressed`() {
        viewModel.handleHomeNavigation()
        assertThat(viewModel.navigate.value, `is`(R.id.navigation_fixtures))
    }

}