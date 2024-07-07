package com.vl.aviatickets.ui.hilt

import com.vl.aviatickets.data.HistoryStoreImpl
import com.vl.aviatickets.data.api.AviaTicketsDao
import com.vl.aviatickets.domain.boundary.HistoryStore
import com.vl.aviatickets.domain.boundary.OffersRepository
import com.vl.aviatickets.domain.boundary.TicketsOffersRepository
import com.vl.aviatickets.domain.boundary.TicketsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DependencyBinder {
    @Binds
    @Singleton
    abstract fun bindHistoryStore(historyStoreImpl: HistoryStoreImpl): HistoryStore

    @Binds
    @Singleton
    abstract fun bindOffersRepository(aviaTicketsDao: AviaTicketsDao): OffersRepository

    @Binds
    @Singleton
    abstract fun bindTicketsRepository(aviaTicketsDao: AviaTicketsDao): TicketsRepository

    @Binds
    @Singleton
    abstract fun bindTicketsOffersRepository(aviaTicketsDao: AviaTicketsDao): TicketsOffersRepository
}