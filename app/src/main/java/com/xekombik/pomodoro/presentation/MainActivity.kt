package com.xekombik.pomodoro.presentation

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.xekombik.pomodoro.R
import com.xekombik.pomodoro.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private var timerIsWorking = false
    private lateinit var timer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        vm.pomodoroTimer = vm.getTimer()
        vm.setGeneralTime(vm.pomodoroTimer)



        refreshValuesOnTextViews()

        binding.changeDurationButton.setOnClickListener {
            vm.changeTimerMode(vm.pomodoroTimer)
            refreshValuesOnTextViews()
            timer = initTimer()
        }

        binding.playPauseButton.setOnClickListener {
            if (timerIsWorking) {
                timer.cancel()
                timer = initTimer()
                stopTimer()
            } else
                playTimer()
        }
        binding.resetButton.setOnClickListener {
            timer.cancel()
            vm.resetTime(vm.pomodoroTimer)
            refreshValuesOnTextViews()
            timerIsWorking = false
            changeButtonsWhenTimerNotWorking()
            timer = initTimer()

        }
    }

    private fun initTimer(): CountDownTimer {
        return object : CountDownTimer(
            vm.pomodoroTimer.time * MILLISECONDS_IN_SECONDS.toLong(),
            MILLISECONDS_IN_SECONDS.toLong()
        ) {
            override fun onTick(millisUntilFinished: Long) {
                vm.pomodoroTimer.time = (millisUntilFinished / MILLISECONDS_IN_SECONDS).toInt()
                binding.timeTextView.text = formatTime(vm.pomodoroTimer.time)

            }

            override fun onFinish() {
                timerIsWorking = false
                changeButtonsWhenTimerNotWorking()
                timer = initTimer()
                vm.changeTimerModeWhenTimerFinished(vm.pomodoroTimer)
                refreshPomodoroProgress()
                refreshValuesOnTextViews()

            }
        }
    }

    private fun playTimer() {
        timer = initTimer()
        timerIsWorking = true
        changeButtonsWhenTimerWorking()
        timer.start()
    }

    private fun stopTimer() {
        timerIsWorking = false
        changeButtonsWhenTimerNotWorking()
        timer.cancel()
        refreshValuesOnTextViews()
    }

    private fun refreshValuesOnTextViews() {
        binding.timeTextView.text = formatTime(vm.pomodoroTimer.time)
        binding.changeDurationButton.text = vm.getTimerMode(vm.pomodoroTimer)
    }

    private fun refreshPomodoroProgress() {

        val pomodoroProgress = listOf(
            binding.pomodoroProgress1,
            binding.pomodoroProgress2,
            binding.pomodoroProgress3,
            binding.pomodoroProgress4
        )
        for (i in 0..3) {
            pomodoroProgress[i].setImageResource(
                if (i < vm.pomodoroTimer.pomodoroCounter) {
                    R.drawable.ellipse_enabled
                } else {
                    R.drawable.ellipse_disabled
                }
            )
        }

    }

    private fun changeButtonsWhenTimerWorking() {
        binding.playPauseButton.setImageResource(R.drawable.baseline_pause_24)
        binding.resetButton.visibility = VISIBLE
        binding.changeDurationButton.isClickable = false
    }

    private fun changeButtonsWhenTimerNotWorking() {
        binding.playPauseButton.setImageResource(R.drawable.baseline_play_arrow_24)
        binding.resetButton.visibility = INVISIBLE
        binding.changeDurationButton.isClickable = true
    }

    private fun formatTime(seconds: Int): String {
        val minutes = seconds / SECONDS_IN_MINUTES
        val remainingSeconds = seconds % SECONDS_IN_MINUTES
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    companion object {
        private const val SECONDS_IN_MINUTES = 60
        private const val MILLISECONDS_IN_SECONDS = 1000
    }
}
