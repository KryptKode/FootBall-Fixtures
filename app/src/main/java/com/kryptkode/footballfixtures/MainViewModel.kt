package com.kryptkode.footballfixtures

import android.app.Application
import android.os.Handler
import androidx.lifecycle.LiveData
import com.kryptkode.footballfixtures.app.base.viewmodel.BaseViewModel
import com.kryptkode.footballfixtures.app.utils.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {
    private val _showExitPrompt: SingleLiveEvent<Unit> = SingleLiveEvent()
    val showExitPrompt: LiveData<Unit> = _showExitPrompt

    private val _exit = SingleLiveEvent<Unit>()
    val exit: LiveData<Unit> = _exit

    private val _navigate = SingleLiveEvent<Int>()
    val navigate: LiveData<Int> = _navigate

    private var doubleBackToExitPressedOnce: Boolean = false

    fun handleOnBackPressed() {
        if (doubleBackToExitPressedOnce) {
            _exit.value = Unit
        }
        this.doubleBackToExitPressedOnce = true
        _showExitPrompt.value = Unit
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, EXIT_PROMPT_DURATION)
    }

    fun handleNavItemSelected(itemId: Int) {
        _navigate.value = itemId
    }

    fun handleHomeNavigation(){
        _navigate.value = R.id.navigation_fixtures
    }

    companion object{
        const val EXIT_PROMPT_DURATION = 2000L
    }
}