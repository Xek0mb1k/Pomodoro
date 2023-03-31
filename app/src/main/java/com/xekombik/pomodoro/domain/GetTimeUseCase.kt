package com.xekombik.pomodoro.domain

class GetTimeUseCase(private val timerRepository: TimerRepository) {
    fun getTime(pomodoroTimer:PomodoroTimer): Int{
        return timerRepository.getTime(pomodoroTimer)
    }
}