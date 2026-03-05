package com.tuapp.timepace.di

import com.tuapp.timepace.domain.usecase.PaceToSpeedUseCase
import com.tuapp.timepace.domain.usecase.PaceToTimeUseCase
import com.tuapp.timepace.domain.usecase.SpeedToPaceUseCase
import com.tuapp.timepace.domain.usecase.TimeToPaceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun providePaceToTimeUseCase(): PaceToTimeUseCase = PaceToTimeUseCase()

    @Provides @Singleton
    fun provideTimeToPaceUseCase(): TimeToPaceUseCase = TimeToPaceUseCase()

    @Provides @Singleton
    fun provideSpeedToPaceUseCase(): SpeedToPaceUseCase = SpeedToPaceUseCase()

    @Provides @Singleton
    fun providePaceToSpeedUseCase(): PaceToSpeedUseCase = PaceToSpeedUseCase()
}
