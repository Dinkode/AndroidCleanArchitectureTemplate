package com.example.architecture.di

import com.example.architecture.items.domain.repository.ItemRepository
import com.example.architecture.items.domain.use_cases.GetItemDetailsUseCase
import com.example.architecture.items.domain.use_cases.GetItemsUseCase
import com.example.architecture.items.domain.use_cases.ItemUseCases
import com.example.architecture.login.domain.repository.UserRepository
import com.example.architecture.login.domain.use_cases.GetUserUseCase
import com.example.architecture.login.domain.use_cases.LoginUseCases
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
    fun provideItemUseCases(repository: ItemRepository): ItemUseCases {
        return ItemUseCases(
           getItems = GetItemsUseCase(repository),
            getItemDetails = GetItemDetailsUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideLoginUseCases(repository: UserRepository): LoginUseCases {
        return LoginUseCases(
            login = GetUserUseCase(repository)
        )
    }
}