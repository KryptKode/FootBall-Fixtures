package com.kryptkode.footballfixtures.app.data.repo

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kryptkode.footballfixtures.app.data.mock.FakeDataSource
import com.kryptkode.footballfixtures.app.data.mock.FakeDataSourceFactory
import com.kryptkode.footballfixtures.app.data.models.Listing
import com.kryptkode.footballfixtures.app.data.models.competition.Competition
import com.kryptkode.footballfixtures.app.data.models.fixtures.Match
import com.kryptkode.footballfixtures.app.data.models.squad.Squad
import com.kryptkode.footballfixtures.app.data.models.table.Table
import com.kryptkode.footballfixtures.app.data.models.team.Team
import javax.inject.Inject

class TestAppRepository @Inject constructor(private val fakeDataSource: FakeDataSource) :
    Repository {

    companion object {
        const val DATA_SIZE = 5
    }

    override fun getCompetitions(): Listing<Competition> {
        val dataSourceFactory =
            FakeDataSourceFactory(fakeDataSource.getCompetitions(DATA_SIZE))
        return createListing(dataSourceFactory)
    }

    override fun getMatches(): Listing<Match> {
        val dataSourceFactory =
            FakeDataSourceFactory(fakeDataSource.getFakeMatches(DATA_SIZE))
        return createListing(dataSourceFactory)
    }

    override fun getTableForCompetition(competitionId: Int?): Listing<Table> {
        val dataSourceFactory =
            FakeDataSourceFactory(fakeDataSource.getFakeTable(DATA_SIZE))
        return createListing(dataSourceFactory)
    }

    override fun getTeamsForCompetition(competitionId: Int?): Listing<Team> {
        val dataSourceFactory =
            FakeDataSourceFactory(fakeDataSource.getFakeTeams(DATA_SIZE))
        return createListing(dataSourceFactory)
    }

    override fun getSquadForTeam(teamId: Int?): Listing<Squad> {
        val dataSourceFactory =
            FakeDataSourceFactory(fakeDataSource.getFakeSquad(DATA_SIZE))
        return createListing(dataSourceFactory)
    }

    override fun getFixturesForCompetition(competitionId: Int?): Listing<Match> {
        val dataSourceFactory =
            FakeDataSourceFactory(fakeDataSource.getFakeMatches(DATA_SIZE))
        return createListing(dataSourceFactory)
    }

    private fun <T> createListing(dataSourceFactory: FakeDataSourceFactory<T>): Listing<T> {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(AppRepository.DATABASE_INITIAL_PAGE_SIZE)
            .setPageSize(AppRepository.DATABASE_PAGE_SIZE)
            .build()

        // Get the paged list
        val builder = LivePagedListBuilder(dataSourceFactory, pagedListConfig)


        val data = builder.build()

        // Get the network errors exposed by the boundary callback
        return Listing(
            data = data,
            refreshState = dataSourceFactory.factory.networkState,
            refresh = {
                dataSourceFactory.factory.invalidate()
            },
            disposable = dataSourceFactory.factory.disposable
        )
    }
}