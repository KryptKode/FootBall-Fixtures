package com.kryptkode.footballfixtures.app.base.viewmodel

import com.kryptkode.footballfixtures.app.data.repo.Repository

abstract class BaseRepoViewModel (protected val repository: Repository) : BaseViewModel()