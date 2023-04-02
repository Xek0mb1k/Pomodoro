package com.xekombik.pomodoro.presentation

import androidx.lifecycle.ViewModel

import com.xekombik.pomodoro.domain.*

class MainViewModel(private val repository: TimerRepository): ViewModel() {


    private val getTimerUseCase = GetTimerUseCase(repository)
    private val getGeneralTimeUseCase = GetGeneralTimeUseCase(repository)

    private val resetTimeUseCase = ResetTimeUseCase(repository)

    private val addPomodoroCounterUseCase = AddPomodoroCounterUseCase(repository)

    var time = 0
    fun getTimer(): PomodoroTimer{
        return getTimerUseCase.getTimer()
    }

    fun getGeneralTime(pomodoroTimer: PomodoroTimer){
        time = getGeneralTimeUseCase.getGeneralTime(pomodoroTimer)
    }
    fun resetTime(pomodoroTimer: PomodoroTimer){
        resetTimeUseCase.resetTime(pomodoroTimer)
    }

    fun addPomodoroCounter(pomodoroTimer: PomodoroTimer){
        addPomodoroCounterUseCase.addCounter(pomodoroTimer)
    }

}