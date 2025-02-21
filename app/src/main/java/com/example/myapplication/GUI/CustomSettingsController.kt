package com.example.myapplication.GUI

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.R

class CustomSettingsController:ComponentActivity() {
    private lateinit var seekBar:SeekBar
    private lateinit var seekBarValue:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.custom_quiz_settings_layout)
        seekBar = findViewById<SeekBar>(R.id.seekBar)
        seekBarValue = findViewById<TextView>(R.id.seekBarValue)
        setupSeekBar()
// Update text position dynamically

    }
    private fun setupSeekBar(){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                (progress + 1).toString().also { seekBarValue.text = it }

                // Show number above the thumb
                val thumbX = seekBar?.thumb?.bounds?.centerX() ?: 0
                seekBarValue.translationX = thumbX.toFloat() - seekBarValue.width / 2
                seekBarValue.visibility = View.VISIBLE
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                seekBarValue.visibility = View.VISIBLE
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBarValue.visibility = View.GONE // Hide number after release
            }
        })
    }
}