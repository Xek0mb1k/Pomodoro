package com.xekombik.pomodoro.domain

interface TimerRepository {

    fun getTimer(): PomodoroTimer
    fun getGeneralTime(pomodoroTimer: PomodoroTimer): Int
    fun getCurrentTime():Int
    fun setTime(pomodoroTimer: PomodoroTimer, time: Int)
    fun resetTime(pomodoroTimer:PomodoroTimer)
    fun changeTimerMode(pomodoroTimer:PomodoroTimer)
    fun addPomodoro(pomodoroTimer:PomodoroTimer)
}