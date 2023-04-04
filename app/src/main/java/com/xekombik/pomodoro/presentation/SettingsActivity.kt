package com.xekombik.pomodoro.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xekombik.pomodoro.R
import com.xekombik.pomodoro.databinding.ActivityMainBinding
import com.xekombik.pomodoro.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {
    private var pomodoroTime = 0
    private var breakTime = 0
    private var longBreakTime = 0
    private lateinit var binding: SettingsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        pomodoroTime = intent.getIntExtra("pomodoroTime", 0)
        breakTime = intent.getIntExtra("breakTime", 0)
        longBreakTime = intent.getIntExtra("longBreakTime", 0)

        refreshValuesOnTextView()

        setAddAndReduceButtonsClickListeners()

    }

    private fun setAddAndReduceButtonsClickListeners() {
        val reduceButtons = listOf(
            binding.reducePomodoroTimeButton,
            binding.reduceBreakTimeButton,
            binding.reduceLongBreakTimeButton
        )
        val addButtons = listOf(
            binding.addPomodoroTimeButton,
            binding.addBreakTimeButton,
            binding.addLongBreakTimeButton
        )

        for (i in 0..2) {
            reduceButtons[i].setOnClickListener {
                when (i) {
                    0 -> if (pomodoroTime > 1)
                        pomodoroTime--
                    1 -> if (breakTime > 1)
                        breakTime--
                    2 -> if (longBreakTime > 1)
                        longBreakTime--
                }
                refreshValuesOnTextView()
            }
            addButtons[i].setOnClickListener {
                when (i) {
                    0 -> pomodoroTime++
                    1 -> breakTime++
                    2 -> longBreakTime++
                }
                refreshValuesOnTextView()
            }
        }
    }

    private fun refreshValuesOnTextView() {
        binding.pomodoroDurationTextView.text = pomodoroTime.toString()
        binding.breakDurationTextView.text = breakTime.toString()
        binding.longBreakDurationTextView.text = longBreakTime.toString()
    }
}
