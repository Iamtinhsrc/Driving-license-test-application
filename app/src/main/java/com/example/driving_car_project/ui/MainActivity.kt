package com.example.driving_car_project.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.driving_car_project.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}