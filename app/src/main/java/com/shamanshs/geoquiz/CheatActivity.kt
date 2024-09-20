package com.shamanshs.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val EXTRA_ANSWER_IS_TRUE = "com.shamanshs.android.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.shamanshs.android.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {

    private var answerIsTrue = false

    private lateinit var showAnswerButton: Button
    private lateinit var answerTextView: TextView
    private lateinit var sdk_text_view: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cheat)
        sdk_text_view = findViewById(R.id.sdk_textView)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        sdk_text_view.append(Build.VERSION.SDK_INT.toString() + " SDK version")

        showAnswerButton.setOnClickListener{
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShowResult(true)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    private fun setAnswerShowResult(inAnswerShow: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, inAnswerShow)
        }
        setResult(Activity.RESULT_OK, data)
    }
}