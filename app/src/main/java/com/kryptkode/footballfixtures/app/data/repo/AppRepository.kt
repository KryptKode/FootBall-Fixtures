package com.kryptkode.footballfixtures.app.data.repo

import android.content.Context
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kryptkode.footballfixtures.app.data.api.ApiManager
import com.kryptkode.footballfixtures.app.data.callbacks.*
import com.kryptkode.footballfixtures.app.data.db.DbManager
import com.kryptkode.footballfixtures.app.data.models.Listing
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.app.data.models.table.Table
import com.kryptkode.footballfixtures.app.data.models.team.Team
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val schedulers: AppSchedulers,
    private val dbManager: DbManager,
    private val apiManager: ApiManager,
    private val context: Context
) {

    /**
     * Get competitions
     */
    fun getCompetitions(): Listing<Competition> {

        // Get data source factory from the local cache
        val dataSourceFactory = dbManager.getCompetitions()


        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(DATABASE_INITIAL_PAGE_SIZE)
            .setPageSize(DATABASE_PAGE_SIZE)
            .build()


        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        val boundaryCallback =
            CompetitionBoundaryCallback(
                schedulers,
                apiManager,
                dbManager,
                context
            )
        val networkState = boundaryCallback.networkState


        // Get the paged list
        val builder = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
            .setBoundaryCallback(boundaryCallback)

        val data = builder.build()


        // Get the network errors exposed by the boundary callback
        return Listing(
            data = data,
            refreshState = networkState,
            refresh = {
                boundaryCallback.onZeroItemsLoaded()
            },
            disposable = boundaryCallback.disposable
        )
    }


    /**
     * Get today's fixtures
     */
    fun getMatches(): Listing<Match> {
        // Get data source factory from the local cache
        val dataSourceFactory = dbManager.getMatches()


        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(DATABASE_INITIAL_PAGE_SIZE)
            .setPageSize(DATABASE_PAGE_SIZE)
            .build()


        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        val boundaryCallback =
            MatchesBoundaryCallback(
                schedulers,
                apiManager,
                dbManager,
                context
            )
        val networkState = boundaryCallback.networkState


        // Get the paged list
        val builder = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
            .setBoundaryCallback(boundaryCallback)

        val data = builder.build()


        // Get the network errors exposed by the boundary callback
        return Listing(
            data = data,
            refreshState = networkState,
            refresh = {
                boundaryCallback.onZeroItemsLoaded()
            },
            disposable = boundaryCallback.disposable
        )
    }


    /**
     * Get table for a particular competition
     * @param competitionId The ID of the competition
     */
    fun getTableForCompetition(competitionId: Int?): Listing<Table> {
        // Get data source factory from the local cache
        val dataSourceFactory = dbManager.getTables(competitionId)


        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(DATABASE_INITIAL_PAGE_SIZE)
            .setPageSize(DATABASE_PAGE_SIZE)
            .build()


        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        val boundaryCallback =
            TableBoundaryCallback(
                schedulers,
                apiManager,
                dbManager,
                context,
                competitionId
            )
        val networkState = boundaryCallback.networkState


        // Get the paged list
        val builder = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
            .setBoundaryCallback(boundaryCallback)

        val data = builder.build()


        // Get the network errors exposed by the boundary callback
        return Listing(
            data = data,
            refreshState = networkState,
            refresh = {
                boundaryCallback.deleteAndRefresh()
            },
            disposable = boundaryCallback.disposable
        )
    }


    /**
     * Get team data for a particular competition
     * @param competitionId The ID of the competition
     */
    fun getTeamsForCompetition(competitionId: Int?): Listing<Team> {
        // Get data source factory from the local cache
        val dataSourceFactory = dbManager.getTeams(competitionId)


        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(DATABASE_INITIAL_PAGE_SIZE)
            .setPageSize(DATABASE_PAGE_SIZE)
            .build()


        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        val boundaryCallback =
            TeamBoundaryCallback(
                schedulers,
                apiManager,
                dbManager,
                context,
                competitionId
            )
        val networkState = boundaryCallback.networkState


        // Get the paged list
        val builder = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
            .setBoundaryCallback(boundaryCallback)

        val data = builder.build()


        // Get the network errors exposed by the boundary callback
        return Listing(
            data = data,
            refreshState = networkState,
            refresh = {
                boundaryCallback.onZeroItemsLoaded()
            },
            disposable = boundaryCallback.disposable
        )
    }


    /**
     * Get squad data for a particular team
     * @param team The team
     */
    fun getSquadForTeam(team: Team?): Listing<Squad> {
        // Get data source factory from the local cache
        val dataSourceFactory = dbManager.getSquad(team?.id)


        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(DATABASE_INITIAL_PAGE_SIZE)
            .setPageSize(DATABASE_PAGE_SIZE)
            .build()


        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        val boundaryCallback =
            SquadBoundaryCallback(
                schedulers,
                apiManager,
                dbManager,
                context,
                team
            )
        val networkState = boundaryCallback.networkState


        // Get the paged list
        val builder = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
            .setBoundaryCallback(boundaryCallback)

        val data = builder.build()


        // Get the network errors exposed by the boundary callback
        return Listing(
            data = data,
            refreshState = networkState,
            refresh = {
                boundaryCallback.onZeroItemsLoaded()
            },
            disposable = boundaryCallback.disposable
        )
    }


    companion object {
        private const val DATABASE_PAGE_SIZE = 10
        private const val DATABASE_INITIAL_PAGE_SIZE = 20
    }

}