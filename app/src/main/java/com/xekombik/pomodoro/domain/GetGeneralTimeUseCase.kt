package com.xekombik.pomodoro.domain

class GetGeneralTimeUseCase(private val timerRepository: TimerRepository) {
    fun getGeneralTime(pomodoroTimer:PomodoroTimer): Int{
        return timerRepository.getGeneralTime(pomodoroTimer)
    }
}
