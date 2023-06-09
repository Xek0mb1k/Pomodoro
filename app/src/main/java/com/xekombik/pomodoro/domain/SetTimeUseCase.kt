package com.xekombik.pomodoro.domain

class SetTimeUseCase(private val timerRepository: TimerRepository) {
    fun setTime(pomodoroTimer: PomodoroTimer, time: Int) {
        timerRepository.setTime(pomodoroTimer, time)
    }
}