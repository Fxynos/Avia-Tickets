package com.vl.aviatickets.ui.hilt

import android.content.Context
import com.vl.aviatickets.data.DataStoreHistoryCache
import com.vl.aviatickets.data.api.AviaTicketsDao
import com.vl.aviatickets.domain.boundary.HistoryCache
import com.vl.aviatickets.domain.boundary.OffersRepository
import com.vl.aviatickets.domain.boundary.TicketsOffersRepository
import com.vl.aviatickets.domain.boundary.TicketsRepository
import com.vl.aviatickets.domain.manager.HistoryManager
import com.vl.aviatickets.domain.manager.OffersManager
import com.vl.aviatickets.domain.manager.TicketsManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped

private const val HISTORY_MAX_CAPACITY = 5 // arrival cities' history

@Module
@InstallIn(FragmentComponent::class)
abstract class DependencyHolder {

    /* Use Case */

    @Provides
    @FragmentScoped
    fun provideHistoryManager(cache: HistoryCache<String>) =
        HistoryManager(cache, HISTORY_MAX_CAPACITY)

    @Provides
    @FragmentScoped
    fun provideOffersManager(offersRepository: OffersRepository) =
        OffersManager(offersRepository)

    @Provides
    @FragmentScoped
    fun provideOffersManager(
        ticketsRepository: TicketsRepository,
        ticketsOffersRepository: TicketsOffersRepository
    ) = TicketsManager(ticketsRepository, ticketsOffersRepository)

    /* Boundary Interface */

    @Binds
    @FragmentScoped
    abstract fun bindOffersRepository(aviaTicketsDao: AviaTicketsDao): OffersRepository

    @Binds
    @FragmentScoped
    abstract fun bindTicketsRepository(aviaTicketsDao: AviaTicketsDao): TicketsRepository

    @Binds
    @FragmentScoped
    abstract fun bindTicketsOffersRepository(aviaTicketsDao: AviaTicketsDao): TicketsOffersRepository

    @Provides
    @FragmentScoped
    fun provideHistoryCache(@ApplicationContext context: Context): HistoryCache<String> =
        DataStoreHistoryCache(context)

    /* Data layer */

    @Provides
    @FragmentScoped
    fun provideAviaTicketsDao(@ApplicationContext context: Context) = AviaTicketsDao
}