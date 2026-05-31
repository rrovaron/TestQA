package com.example.testform.di

import com.example.testform.data.repository.RegistrationRepositoryImpl
import com.example.testform.domain.repository.RegistrationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindRegistrationRepository(
        registrationRepositoryImpl: RegistrationRepositoryImpl
    ): RegistrationRepository
}
