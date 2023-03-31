package com.xekombik.pomodoro.domain

data class PomodoroTimer(

    val pomodoroTime: Int,
    val breakTime: Int,
    val longBreakTime: Int,

    val timerMode: String,
    val pomodoroCounter: Int

)
