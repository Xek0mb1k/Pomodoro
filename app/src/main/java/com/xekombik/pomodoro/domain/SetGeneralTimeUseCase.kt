package com.xekombik.pomodoro.domain

class SetGeneralTimeUseCase(private val timerRepository: TimerRepository) {
    fun getGeneralTime(pomodoroTimer:PomodoroTimer): Int{
        return timerRepository.setGeneralTime(pomodoroTimer)
    }
}
