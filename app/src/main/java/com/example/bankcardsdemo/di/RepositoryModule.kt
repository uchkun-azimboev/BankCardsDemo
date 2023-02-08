package com.example.bankcardsdemo.di

import android.app.Application
import com.example.bankcardsdemo.repository.CardRepository
import com.example.bankcardsdemo.repository.CardRepositoryImp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCardRepository(database: FirebaseDatabase, context: Application): CardRepository {
        return CardRepositoryImp(database, context)
    }
}