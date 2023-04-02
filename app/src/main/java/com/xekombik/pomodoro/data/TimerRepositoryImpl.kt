package com.xekombik.pomodoro.data

import com.xekombik.pomodoro.domain.PomodoroTimer
import com.xekombik.pomodoro.domain.TimerRepository

object TimerRepositoryImpl : TimerRepository {

    private var timer = PomodoroTimer(25 * 60, 5 * 60, 15 * 60)

    init {
        resetTime(timer)
    }

    override fun getTimer(): PomodoroTimer {
        return timer
    }


    override fun getGeneralTime(pomodoroTimer: PomodoroTimer): Int {
        return when (pomodoroTimer.timerMode) {
            POMODORO_TIME_ID -> pomodoroTimer.pomodoroTime
            BREAK_TIME_ID -> pomodoroTimer.breakTime
            LONG_BREAK_TIME_ID -> pomodoroTimer.longBreakTime
            else -> throw java.lang.RuntimeException("Element $this not found")
        }

    }

    override fun getCurrentTime(): Int {
        return timer.time
    }


    override fun resetTime(pomodoroTimer: PomodoroTimer) {
        pomodoroTimer.time = getGeneralTime(pomodoroTimer)
    }

    override fun setTime(pomodoroTimer: PomodoroTimer, time: Int) {
        pomodoroTimer.time = time
    }

    override fun changeTimerMode(pomodoroTimer: PomodoroTimer) {
        pomodoroTimer.timerMode++
        if (pomodoroTimer.timerMode > BREAK_TIME_ID){
            pomodoroTimer.timerMode = POMODORO_TIME_ID
            addPomodoro(pomodoroTimer)
        }
    }

    override fun addPomodoro(pomodoroTimer: PomodoroTimer) {
        pomodoroTimer.pomodoroCounter++
        if (pomodoroTimer.pomodoroCounter > 4) {
            pomodoroTimer.pomodoroCounter = 0
            pomodoroTimer.timerMode = LONG_BREAK_TIME_ID
        }
    }

    private const val POMODORO_TIME_ID = 0
    private const val BREAK_TIME_ID = 1
    private const val LONG_BREAK_TIME_ID = 2
}

