package com.xekombik.pomodoro.domain

data class PomodoroTimer(

    val pomodoroTime: Int,
    val breakTime: Int,
    val longBreakTime: Int,


    var time: Int = 0,
    var timerMode: Int = 0,
    var pomodoroCounter: Int = 0

)
