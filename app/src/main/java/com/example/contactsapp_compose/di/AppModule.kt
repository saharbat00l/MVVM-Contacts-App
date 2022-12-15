package com.example.contactsapp_compose.di

import android.app.Application
import androidx.room.Room
import com.example.contactsapp_compose.data.ContactDatabase
import com.example.contactsapp_compose.data.ContactRepoImplementation
import com.example.contactsapp_compose.data.ContactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContactDatabase(app: Application): ContactDatabase{
        return Room.databaseBuilder(
            app,
            ContactDatabase::class.java,
            "contact_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactRepository(db: ContactDatabase): ContactRepository{
        return ContactRepoImplementation(db.dao)
    }
}