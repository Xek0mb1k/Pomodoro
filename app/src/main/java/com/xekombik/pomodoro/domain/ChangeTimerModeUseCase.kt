package com.xekombik.pomodoro.domain

class ChangeTimerModeUseCase(private val timerRepository: TimerRepository) {
    fun changeTimerMode(pomodoroTimer:PomodoroTimer){
        timerRepository.changeTimerMode(pomodoroTimer)
    }
}