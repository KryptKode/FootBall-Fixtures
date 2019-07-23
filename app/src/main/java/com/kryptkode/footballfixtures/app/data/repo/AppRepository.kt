package com.kryptkode.footballfixtures.app.data.repo

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
import com.kryptkode.footballfixtures.app.utils.ErrorHandler
import com.kryptkode.footballfixtures.app.utils.schedulers.AppSchedulers
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val schedulers: AppSchedulers,
    private val dbManager: DbManager,
    private val apiManager: ApiManager,
    private val errorHandler: ErrorHandler
) : Repository {

    /**
     * Get competitions
     */
    override fun getCompetitions(): Listing<Competition> {

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
                errorHandler
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
    override fun getMatches(): Listing<Match> {
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
                errorHandler
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
    override fun getTableForCompetition(competitionId: Int?): Listing<Table> {
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
                competitionId,
                schedulers,
                apiManager,
                dbManager,
                errorHandler
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
    override fun getTeamsForCompetition(competitionId: Int?): Listing<Team> {
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
                competitionId,
                schedulers,
                apiManager,
                dbManager,
                errorHandler
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
     * @param teamId The team ID
     */
    override fun getSquadForTeam(teamId: Int?): Listing<Squad> {
        // Get data source factory from the local cache
        val dataSourceFactory = dbManager.getSquad(teamId)


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
                teamId,
                schedulers,
                apiManager,
                dbManager,
                errorHandler
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
     * Get matches data for a particular competition
     * @param competitionId The ID of the competition
     */
    override fun getFixturesForCompetition(competitionId: Int?): Listing<Match> {
        // Get data source factory from the local cache
        val dataSourceFactory = dbManager.getMatchesForCompetition(competitionId)


        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(DATABASE_INITIAL_PAGE_SIZE)
            .setPageSize(DATABASE_PAGE_SIZE)
            .build()


        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        val boundaryCallback =
            FixturesBoundaryCallback(
                competitionId,
                schedulers,
                apiManager,
                dbManager,
                errorHandler
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
        const val DATABASE_PAGE_SIZE = 10
        const val DATABASE_INITIAL_PAGE_SIZE = 20
    }

}