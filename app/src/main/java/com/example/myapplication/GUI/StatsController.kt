package com.example.myapplication.GUI

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.R
import com.example.myapplication.Repositories.QuizResultsDBRepository
import com.example.myapplication.Service.GeneralService
import com.example.myapplication.Service.QuizResultsService
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF

class StatsController:ComponentActivity() {
    private lateinit var playedText:TextView
    private lateinit var passedText:TextView
    private lateinit var failedText:TextView
    private lateinit var mainMenuButton:Button
    private lateinit var statsService: QuizResultsService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val statsRepo=QuizResultsDBRepository(this)
        statsService=QuizResultsService(statsRepo)
        setContentView(R.layout.stats_layout)
        val pieChart: PieChart = findViewById(R.id.piechart)
        setupPieChart(pieChart,statsService.getPassedQuizes().size,statsService.getFailedQuizes().size)
        initButtonsAndTexts()
    }
    private fun initButtonsAndTexts(){
        playedText=findViewById(R.id.playedText)
        passedText=findViewById(R.id.passedText)
        failedText=findViewById(R.id.failedText)
        val totalQuizes = statsService.getFailedQuizes().size + statsService.getPassedQuizes().size
        val failedQuizes = statsService.getFailedQuizes().size
        val passedQuizes = statsService.getPassedQuizes().size

        val playedSpannable = SpannableString("$totalQuizes\nQuizes Played")
        playedSpannable.setSpan(RelativeSizeSpan(1.5f), 0, totalQuizes.toString().length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        playedText.text = playedSpannable

        val failedSpannable = SpannableString("$failedQuizes\nFailed")
        failedSpannable.setSpan(RelativeSizeSpan(1.5f), 0, failedQuizes.toString().length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        failedText.text = failedSpannable

        val passedSpannable = SpannableString("$passedQuizes\nPassed")
        passedSpannable.setSpan(RelativeSizeSpan(1.5f), 0, passedQuizes.toString().length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        passedText.text = passedSpannable
        mainMenuButton=findViewById(R.id.mainMenuButton)
        mainMenuButton.setOnClickListener{
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
    private fun setupPieChart(pieChart: PieChart,passed:Int,failed:Int){
        val entries = mutableListOf<PieEntry>()
        if (passed > 0) entries.add(PieEntry(passed.toFloat(), "Passed"))
        if (failed > 0) entries.add(PieEntry(failed.toFloat(), "Failed"))
        val dataSet = PieDataSet(entries, "Quiz Results")
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.holeRadius = 40f

        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.transparentCircleRadius = 38f
        dataSet.colors = listOf(Color.rgb(0,157,196), Color.rgb(221,48,28))
        dataSet.valueTextColor = R.color.white_gray_for_buttons

        dataSet.valueTextSize=16f
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        val pieData = PieData(dataSet)
        pieData.setValueFormatter(PercentFormatter())
        pieData.setValueTextSize(15f)
        pieData.setValueTypeface(Typeface.DEFAULT_BOLD)
        pieData.setValueTextColor(Color.WHITE)


        // undo all highlights
        pieChart.highlightValues(null)
        pieChart.data = pieData

        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.animateY(1000, Easing.EaseInOutQuad)
        pieChart.highlightValues(null)
        pieChart.description.isEnabled = false
        pieChart.invalidate()

    }
}