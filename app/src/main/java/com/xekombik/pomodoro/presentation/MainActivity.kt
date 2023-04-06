package com.xekombik.pomodoro.presentation

import android.Manifest.permission.VIBRATE
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.xekombik.pomodoro.R
import com.xekombik.pomodoro.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private var timerIsWorking = false
    private lateinit var timer: CountDownTimer
    private lateinit var prefs: SharedPreferences

    private var soundIsOn = true
    private var vibrateIsOn = false
    private var autostartBreaksIsOn = false
    private var autostartPomodoroIsOn = false
    private var keepPhoneAwakeIsOn = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        prefs = getSharedPreferences("Settings", Context.MODE_PRIVATE)


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

        binding.settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("pomodoroTime", vm.pomodoroTimer.pomodoroTime / SECONDS_IN_MINUTES)
            intent.putExtra("breakTime", vm.pomodoroTimer.breakTime / SECONDS_IN_MINUTES)
            intent.putExtra("longBreakTime", vm.pomodoroTimer.longBreakTime / SECONDS_IN_MINUTES)
            startActivity(intent)
        }

    }

    override fun onResume() {
        val pomodoroTime = prefs.getInt("pomodoroTime", 25 * 60)
        val breakTime = prefs.getInt("breakTime", 5 * 60)
        val longBreakTime = prefs.getInt("longBreakTime", 15 * 60)

        soundIsOn = prefs.getBoolean("soundIsOn", soundIsOn)
        vibrateIsOn = prefs.getBoolean("vibrateIsOn", vibrateIsOn)
        autostartBreaksIsOn = prefs.getBoolean("autostartBreaksIsOn", autostartBreaksIsOn)
        autostartPomodoroIsOn = prefs.getBoolean("autostartPomodoroIsOn", autostartPomodoroIsOn)
        keepPhoneAwakeIsOn = prefs.getBoolean("keepPhoneAwakeIsOn", keepPhoneAwakeIsOn)

        if (keepPhoneAwakeIsOn)
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        else
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        vm.pomodoroTimer.pomodoroTime = pomodoroTime
        vm.pomodoroTimer.breakTime = breakTime
        vm.pomodoroTimer.longBreakTime = longBreakTime
        vm.setGeneralTime(vm.pomodoroTimer)


        refreshValuesOnTextViews()
        super.onResume()
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
                if (soundIsOn)
                    playSong()


                if (vibrateIsOn) {

                        vibrate()


                }

                timerIsWorking = false
                changeButtonsWhenTimerNotWorking()
                timer = initTimer()
                vm.changeTimerModeWhenTimerFinished(vm.pomodoroTimer)
                refreshPomodoroProgress()
                refreshValuesOnTextViews()

                if (autostartBreaksIsOn &&
                    (vm.pomodoroTimer.timerMode == 1 || vm.pomodoroTimer.timerMode == 2)
                )
                    playTimer()

                if (autostartPomodoroIsOn && vm.pomodoroTimer.timerMode == 0)
                    playTimer()

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

    private fun playSong(){
        val mediaPlayer = MediaPlayer.create(applicationContext, R.raw.bell);
        mediaPlayer.start();
    }



    private fun vibrate(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorManager.defaultVibrator;

            val timings: LongArray = longArrayOf(0, 200, 10, 500)

            vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
            // TODO("FIX THIS")
        } else {
            val vibratorService = getSystemService(VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(1000)
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
