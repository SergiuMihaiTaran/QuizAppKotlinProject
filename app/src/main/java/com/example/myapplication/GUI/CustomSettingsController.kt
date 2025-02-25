package com.example.myapplication.GUI

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.isVisible
import com.example.myapplication.R

class CustomSettingsController:ComponentActivity() {
    private lateinit var seekBar:SeekBar
    private lateinit var seekBarValue:TextView
    private lateinit var editTextTitle:EditText
    private lateinit var editTextDescription:EditText
    private lateinit var mainMenuButton: Button
    private lateinit var createQuizButton:Button
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.custom_quiz_settings_layout)
        setupViews()
        setupSeekBar()
        setupButtons()

    }
    private fun setupButtons(){
        mainMenuButton.setOnClickListener{
            Intent(this, MainActivity::class.java).also {
                startActivity(it)}
        }
        createQuizButton.setOnClickListener {
            val intent = Intent(this, AddQuestionController::class.java).apply {
                putExtra("QUESTIONS_NUMBER", seekBar.progress + 1)
                putExtra("TITLE", editTextTitle.text.toString())
                putExtra("DESCRIPTION", editTextDescription.text.toString())
            }
            startActivity(intent)
        }

        createQuizButton.isVisible = false
        editTextTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                createQuizButton.isVisible = !s.isNullOrBlank() // Enable if not empty
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
// Update text position dynamically
        })
    }
    private fun setupViews(){
        editTextTitle=findViewById(R.id.titleEditText)
        editTextDescription=findViewById(R.id.descriptionEditText)
        createQuizButton=findViewById(R.id.createQuizButton)
        mainMenuButton=findViewById(R.id.mainMenuButton)
        seekBar = findViewById(R.id.seekBar)
        seekBarValue = findViewById(R.id.seekBarValue)
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