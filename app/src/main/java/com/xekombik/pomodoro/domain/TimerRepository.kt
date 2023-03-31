package com.xekombik.pomodoro.domain

interface TimerRepository {
    fun getTime(pomodoroTimer: PomodoroTimer): Int
    fun resetTime(pomodoroTimer:PomodoroTimer)
    fun startTimer()
    fun stopTimer()
}