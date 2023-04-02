package com.xekombik.pomodoro.domain

class GetCurrentTimeUseCase(private val timerRepository: TimerRepository) {
    fun getCurrentTime():Int{
        return timerRepository.getCurrentTime()
    }
}