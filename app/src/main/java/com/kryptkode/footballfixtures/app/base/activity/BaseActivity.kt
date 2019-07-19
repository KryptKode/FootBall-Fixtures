package com.kryptkode.footballfixtures.app.base.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer

import com.kryptkode.footballfixtures.app.utils.Constants
import com.kryptkode.footballfixtures.app.utils.NetworkListener
import com.google.android.material.snackbar.Snackbar
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.dialogs.InfoDialog
import com.kryptkode.footballfixtures.app.utils.AttrUtils
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber


abstract class BaseActivity : DaggerAppCompatActivity() {
    protected var snackBar: Snackbar? = null
    protected var firstTime = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
             requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
         } else {
             requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
         }*/
        val networkListener = NetworkListener(this)
        networkListener.networkSubject.observe(this, Observer {
            if (it) {
                onInternetConnectionGained()
            } else {
                onInternetConnectionLost()
            }
        })
        lifecycle.addObserver(networkListener)


    }


    fun showUpEnabled(enabled: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun setActionBarTitle(@StringRes title: Int) {
        supportActionBar?.title = getString(title)
    }

    fun setHomeUpDrawable(@DrawableRes drawable: Int) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(drawable)
    }

    fun setHomeUpDrawable(drawable: Drawable) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(drawable)
    }


    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    fun startNewActivity(name: Class<*>, finish: Boolean = false, data: Bundle? = null) {
        val intent = Intent(this, name)
        intent.putExtra(Constants.EXTRAS, data)
        startActivity(intent)
        if (finish) {
            finish()
        }
    }

    fun startNewActivityWithAnimation(name: Class<*>, anchorView: View, data: Bundle? = null) {
        val intent = Intent(this, name)
        startNewActivityWithAnimation(name, intent, anchorView, data)
    }

    fun startNewActivityWithAnimation(
        name: Class<*>,
        intent: Intent,
        anchorView: View,
        data: Bundle? = null
    ) {
        intent.putExtra(Constants.EXTRAS, data)
        intent.setClass(this, name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, anchorView, anchorView.transitionName
                    ?: return
            ).toBundle()
            startActivity(intent, bundle)
        } else {
            startActivity(intent)
        }
    }


    protected open fun onInternetConnectionLost() {

    }

    protected open fun onInternetConnectionGained() {

    }


    fun notifyUser(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun notifyUser(view: View, message: String) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setBackgroundColor(
            AttrUtils.convertAttrToColor(
                R.attr.colorSecondary,
                view.context
            )
        )
        (snackBarLayout.findViewById<View>(R.id.snackbar_text) as TextView)
            .setTextColor(AttrUtils.convertAttrToColor(R.attr.colorOnSecondary, view.context))
        snackBar.show()
    }


    @Suppress("DEPRECATION")
    fun isOffline(): Boolean {
        val manager = this
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return !(manager.activeNetworkInfo?.isConnectedOrConnecting == true)
    }


    fun showView(view: View, textView: TextView, message: String) {
        textView.text = message

        val bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up)

        view.startAnimation(bottomUp)
        view.visibility = View.VISIBLE
    }

    fun hideView(view: View) {

        val slide = AnimationUtils.loadAnimation(
            this,
            R.anim.up_down
        )
        view.startAnimation(slide)
        view.visibility = View.GONE
    }

    fun showDialog(title: String?, message: String?) {
        val dialogFragment = InfoDialog.newInstance(
            title = title,
            message = message,
            buttonText = getString(android.R.string.ok)
        )
        Timber.e("msg: %s", message)
        dialogFragment.show(supportFragmentManager, dialogFragment.javaClass.name)
    }


    fun openAppSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }


}
