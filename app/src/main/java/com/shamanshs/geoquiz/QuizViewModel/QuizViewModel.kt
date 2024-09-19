package com.shamanshs.geoquiz.QuizViewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.shamanshs.geoquiz.Question
import com.shamanshs.geoquiz.R

private const val TAG = "QuizViewModel"


class QuizViewModel() : ViewModel() {


        private val questionBank = listOf(
        Question(R.string.question1_text, true),
        Question(R.string.question2_text, true),
        Question(R.string.question3_text, false),
        Question(R.string.question4_text, false),
        Question(R.string.question5_text, true),
        Question(R.string.question6_text, true),)

    var answerUserBank: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0 ,0)

    var currentIndex = 0
    var isCheater = false

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToBack() {
        currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
    }

    fun saveAnswerUser(answer: Int) {
        answerUserBank[currentIndex] = answer
    }

    fun checkThisAnswer() : Boolean{
        return answerUserBank[currentIndex] == 0
    }

    fun checkTheAnswer(t: Context){
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
        Toast.makeText(t, resText, Toast.LENGTH_SHORT).show()
    }


}