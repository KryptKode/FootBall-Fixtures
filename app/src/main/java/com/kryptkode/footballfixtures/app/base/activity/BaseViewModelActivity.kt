package com.kryptkode.footballfixtures.app.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kryptkode.footballfixtures.app.base.viewmodel.BaseViewModel
import javax.inject.Inject

abstract class BaseViewModelActivity <D, V> : BaseActivity() where D : ViewDataBinding, V : BaseViewModel {

    protected lateinit var binding: D
    protected lateinit var viewModel: V



    @Inject
    protected lateinit var viewModeFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModeFactory)
            .get(getViewModelClass())
        initBinding()

    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutResourceId())
        binding.setVariable(getBindingVariable(), viewModel)
        binding.executePendingBindings()
    }



    @LayoutRes
    protected abstract fun getLayoutResourceId(): Int

    protected abstract fun getBindingVariable(): Int

    protected abstract fun getViewModelClass(): Class<V>



}