package com.xekombik.pomodoro.di

import com.xekombik.pomodoro.data.TimerRepositoryImpl
import com.xekombik.pomodoro.domain.TimerRepository
import com.xekombik.pomodoro.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TimerRepository> { TimerRepositoryImpl }

    viewModel { MainViewModel(get()) }
}