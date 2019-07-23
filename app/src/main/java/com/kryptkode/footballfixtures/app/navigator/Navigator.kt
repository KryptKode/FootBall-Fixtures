package com.kryptkode.footballfixtures.app.navigator

import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.competitions.CompetitionsFragment
import com.kryptkode.footballfixtures.todaysfixtures.TodaysFixturesFragment
import timber.log.Timber

class Navigator(
    navElements: NavElements
) {

    private val fragmentsFragments = navElements.fragmentsFragments
    private val supportFragmentManager = navElements.supportFragmentManager
    private val navView = navElements.navView
    private val toolbarUtils = navElements.toolbarUtils

    private var homeState: Fragment.SavedState? = null
    private var fragment: Fragment? = null

    fun navigateTo(@IdRes itemId: Int) {
        setNavItemChecked(itemId)
        toolbarUtils.setToolbarTitle(itemId)

        when (itemId) {

            R.id.navigation_fixtures -> {
                if (fragment is TodaysFixturesFragment) {
                    return
                }

                /*  if (!(fragment!!.isAdded)) {
                      Timber.d("Setting intial state...")
                      fragment?.setInitialSavedState(homeState)
                  }
                  */
                supportFragmentManager.popBackStack()
                showFragmentFirst(fragmentsFragments.todaysFixturesFragment)
            }


            R.id.navigation_competitions -> {
                if (fragment is TodaysFixturesFragment) {
                    Timber.d("Saving home fragment state...")
                    homeState = supportFragmentManager.saveFragmentInstanceState(fragment ?: return)
                } else {
                    Timber.d("Not home fragment")
                }

                if (fragment is CompetitionsFragment) {
                    return
                }
                showFragment(fragmentsFragments.competitionsFragment)
            }
        }
    }


    private fun setNavItemChecked(itemId: Int) {
        val newItem = navView.menu.findItem(itemId)
        newItem.isChecked = true
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.addToBackStack("back")
        transaction.replace(R.id.fragment_root, fragment)
        transaction.commit()
    }

    private fun showFragmentFirst(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.replace(R.id.fragment_root, fragment)
        transaction.commit()
    }
}