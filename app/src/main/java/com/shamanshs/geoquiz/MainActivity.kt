package com.shamanshs.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question1_text, true),
        Question(R.string.question2_text, true),
        Question(R.string.question3_text, false),
        Question(R.string.question4_text, false),
        Question(R.string.question5_text, true),
        Question(R.string.question6_text, true),
    )
    private var answerUserBank: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0 ,0)

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        nextButton = findViewById(R.id.nextButton)
        backButton = findViewById(R.id.backButton)
        questionTextView = findViewById(R.id.questionTextView)


        trueButton.setOnClickListener { view: View ->
            if (answerUserBank[currentIndex] == 0) {
                checkAnswer(true)
                checkResultAnswer()
            }
        }
        falseButton.setOnClickListener { view: View ->
            if (answerUserBank[currentIndex] == 0) {
                checkAnswer(false)
                checkResultAnswer()
            }
        }
        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        backButton.setOnClickListener {
            currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart(Bundle?) called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (correctAnswer == userAnswer) {
            answerUserBank[currentIndex] = 1
            R.string.correct_toast
        } else {
            answerUserBank[currentIndex] = -1
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun checkResultAnswer(){
        var res = 0
        for (i in 0..<answerUserBank.size){
            if (answerUserBank[i] == 0){
                return
            }
            if (answerUserBank[i] == 1)
                res += 1
        }
        val resp = ((res * 1.0) / answerUserBank.size) * 100
        val resText = "Correct $resp% Answers"
        Toast.makeText(this, resText, Toast.LENGTH_SHORT).show()
    }
}