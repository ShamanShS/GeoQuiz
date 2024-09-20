package com.shamanshs.geoquiz

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.shamanshs.geoquiz.QuizViewModel.QuizViewModel
import com.shamanshs.geoquiz.QuizViewModel.QuizViewModelFactory


private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_COD_CHEAT = 0

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var cheatNumberTextView: TextView

    private val quizViewModel : QuizViewModel by lazy {
        val factory = QuizViewModelFactory()
        ViewModelProvider(this@MainActivity, factory)[QuizViewModel::class.java]
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == RESULT_OK) {
            if (result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) == true) {
                quizViewModel.isCheater -= 1
                val text = quizViewModel.isCheater.toString() + " hunts"
                cheatNumberTextView.text = text
            }
//             when {
//                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false -> {
//                    quizViewModel.isCheater -= 1
//                }
//                else -> +0
//            }

        }
    }

//    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX,0) ?: 0
        quizViewModel.currentIndex = currentIndex
        Log.d(TAG, "Got a QuizViewModel:$quizViewModel")

        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        nextButton = findViewById(R.id.nextButton)
        backButton = findViewById(R.id.backButton)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.questionTextView)
        cheatNumberTextView = findViewById(R.id.cheat_number_textView)

        cheatNumberTextView.append(quizViewModel.isCheater.toString() + " hunts")
        trueButton.setOnClickListener { view: View ->
            if (quizViewModel.checkThisAnswer()) {
                checkAnswer(true)
                quizViewModel.checkTheAnswer(this)
            }
        }
        falseButton.setOnClickListener { view: View ->
            if (quizViewModel.checkThisAnswer()) {
                checkAnswer(false)
                quizViewModel.checkTheAnswer(this)
            }
        }
        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        backButton.setOnClickListener {
            quizViewModel.moveToBack()
            updateQuestion()
        }
        cheatButton.setOnClickListener{ view: View ->
            if (quizViewModel.isCheater > 0) {
                val answerIsTrue = quizViewModel.currentQuestionAnswer
                val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
                val options = ActivityOptionsCompat.makeClipRevealAnimation(
                    view,
                    0,
                    0,
                    view.width,
                    view.height
                )
//            if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M) проверка на версию SDK
                getResult.launch(intent, options)
            }
        }
        updateQuestion()
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
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

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
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
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater < 3 -> {
                quizViewModel.saveAnswerUser(-1)
                R.string.judgment_toast
            }
            userAnswer == correctAnswer ->{
                quizViewModel.saveAnswerUser(1)
                R.string.correct_toast
            }
            else -> {
                quizViewModel.saveAnswerUser(-1)
                R.string.incorrect_toast
            }

        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun updateTextView(textView: TextView, text: String) {
        textView.append(text)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (result.)
//    }


}