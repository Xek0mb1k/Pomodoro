package com.xekombik.pomodoro.domain

class GetTimerModeUseCase (private val timerRepository: TimerRepository) {
    fun getTimerMode(pomodoroTimer: PomodoroTimer): String{
        return timerRepository.getTimerMode(pomodoroTimer)
    }
}