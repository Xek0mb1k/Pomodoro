package com.xekombik.pomodoro.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xekombik.pomodoro.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {


    private val vm by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        vm.getGeneralTime(vm.getTimer())



    }
}
