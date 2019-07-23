package com.kryptkode.footballfixtures.app.base.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kryptkode.footballfixtures.app.base.activity.BaseActivity
import com.kryptkode.footballfixtures.app.base.viewmodel.BaseViewModel
import com.kryptkode.footballfixtures.app.utils.AppIdlingResource
import com.kryptkode.footballfixtures.app.utils.NetworkUtils
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<D, V> : DaggerFragment() where D : ViewDataBinding, V : BaseViewModel {


    protected var idlingResource: AppIdlingResource? = null

    protected lateinit var binding: D

    @VisibleForTesting
    lateinit var viewModel: V


    @Inject
    protected lateinit var viewModeFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModeFactory)
            .get(getViewModelClass())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBinding(inflater, container)
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false)
        binding.setVariable(getBindingVariable(), viewModel)
        binding.executePendingBindings()
        return binding.root
    }


    protected open val isUserOffline: Boolean
        get() {
            if (NetworkUtils.isOffline(context!!)) {
                //TODO: SHow no internet view
                return true
            }
            return false
        }


    protected open fun showNotification(string: Int) {
        if (activity is BaseActivity) {
            Timber.d("showNotification: showing notification")
            (activity as BaseActivity).notifyUser(getString(string))
        } else {
            logNotBaseActivityError()
        }
    }


    protected open fun showNotification(string: String) {
        if (activity is BaseActivity) {
            Timber.d("showNotification: showing notification")
            (activity as BaseActivity).notifyUser(string)
        } else {
            logNotBaseActivityError()
        }
    }


    protected open fun notifyUser(view: View, string: String) {
        if (activity is BaseActivity) {
            Timber.d("notifyUser: showing notification")
            (activity as BaseActivity).notifyUser(view, string)
        } else {
            logNotBaseActivityError()
        }
    }


    protected fun openAppSettings() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).openAppSettings()
        } else {
            logNotBaseActivityError()
        }
    }


    fun showView(view: View, textView: TextView, message: String) {
        if (activity is BaseActivity) {
            Timber.d("showView: animating view upwards")
            (activity as BaseActivity).showView(view, textView, message)
        } else {
            logNotBaseActivityError()
        }
    }

    fun hideView(view: View) {
        if (activity is BaseActivity) {
            Timber.d("hideView: animating view downwards")
            (activity as BaseActivity).hideView(view)
        } else {
            logNotBaseActivityError()
        }
    }


    protected fun hideKeyBoard(view: View) {
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view.windowToken, 0)
    }


    protected fun startNewActivity(name: Class<*>, finish: Boolean = false, data: Bundle? = null) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).startNewActivity(name, finish, data)
        } else {
            logNotBaseActivityError()
        }
    }

    protected fun startNewActivity(name: Class<*>, finish: Boolean) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).startNewActivity(name, finish)
        } else {
            logNotBaseActivityError()
        }
    }

    protected fun startNewActivity(name: Class<*>) {
        startNewActivity(name, false)
    }

    protected fun startNewActivityWithAnimation(
        name: Class<*>,
        anchorView: View,
        data: Bundle? = null
    ) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).startNewActivityWithAnimation(name, anchorView, data)
        } else {
            logNotBaseActivityError()
        }
    }

    private fun logNotBaseActivityError() {
        Timber.e("Activity is not: %s", BaseActivity::class.java.name)
    }

    protected fun startNewActivityWithAnimation(name: Class<*>, intent: Intent, anchorView: View) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).startNewActivityWithAnimation(name, intent, anchorView)
        } else {
            logNotBaseActivityError()
        }
    }

    fun provideIdlingResource(): AppIdlingResource? {
        if (idlingResource == null) {
            idlingResource = AppIdlingResource()
        }
        return idlingResource
    }

    @LayoutRes
    protected abstract fun getLayoutResourceId(): Int

    protected abstract fun getBindingVariable(): Int

    protected abstract fun getViewModelClass(): Class<V>

}
