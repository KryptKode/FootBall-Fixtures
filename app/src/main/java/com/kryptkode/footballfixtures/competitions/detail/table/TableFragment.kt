package com.kryptkode.footballfixtures.competitions.detail.table

import com.kryptkode.footballfixtures.BR
import com.kryptkode.footballfixtures.R
import com.kryptkode.footballfixtures.app.base.fragment.BaseFragment
import com.kryptkode.footballfixtures.databinding.FragmentTableBinding
import javax.inject.Inject

class TableFragment @Inject constructor() : BaseFragment<FragmentTableBinding, TableViewModel>(){


    override fun getLayoutResourceId() = R.layout.fragment_table

    override fun getBindingVariable() = BR._all

    override fun getViewModelClass() = TableViewModel::class.java
}
