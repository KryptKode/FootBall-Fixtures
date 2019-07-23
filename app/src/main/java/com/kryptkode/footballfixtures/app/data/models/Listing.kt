package com.kryptkode.footballfixtures.app.data.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.kryptkode.footballfixtures.app.utils.NetworkState
import io.reactivex.disposables.Disposable

/**
 * Data class that is necessary for a UI to show a listing and interact w/ the rest of the system
 */
data class Listing<T>(
        // the LiveData of paged lists for the UI to observe
        val data: LiveData<PagedList<T>>,
        // represents the refresh status to show to the user. Separate from networkState, this
        // value is importantly only when refresh is requested.
        val refreshState: LiveData<NetworkState>,
        // refreshes the whole data and fetches it from scratch.
        val refresh: () -> Unit,

        val disposable: Disposable)