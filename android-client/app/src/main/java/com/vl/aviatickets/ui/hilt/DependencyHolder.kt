package com.vl.aviatickets.ui.hilt

import android.content.Context
import com.vl.aviatickets.data.HistoryStoreImpl
import com.vl.aviatickets.data.api.AviaTicketsDao
import com.vl.aviatickets.domain.boundary.HistoryStore
import com.vl.aviatickets.domain.boundary.OffersRepository
import com.vl.aviatickets.domain.boundary.TicketsOffersRepository
import com.vl.aviatickets.domain.boundary.TicketsRepository
import com.vl.aviatickets.domain.manager.HistoryManager
import com.vl.aviatickets.domain.manager.OffersManager
import com.vl.aviatickets.domain.manager.TicketsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val HISTORY_MAX_CAPACITY = 5 // arrival cities' history

@Module
@InstallIn(SingletonComponent::class)
object DependencyHolder {
    /* Use Case */

    @Provides
    @Singleton
    fun provideHistoryManager(cache: HistoryStore) =
        HistoryManager(cache, HISTORY_MAX_CAPACITY)

    @Provides
    @Singleton
    fun provideOffersManager(offersRepository: OffersRepository) =
        OffersManager(offersRepository)

    @Provides
    @Singleton
    fun provideTicketsManager(
        ticketsRepository: TicketsRepository,
        ticketsOffersRepository: TicketsOffersRepository
    ) = TicketsManager(ticketsRepository, ticketsOffersRepository)

    /* Data Model */

    @Provides
    @Singleton
    fun provideAviaTicketsDao() =
        AviaTicketsDao

    @Provides
    @Singleton
    fun provideHistoryStoreImpl(@ApplicationContext context: Context) =
        HistoryStoreImpl(context)
}