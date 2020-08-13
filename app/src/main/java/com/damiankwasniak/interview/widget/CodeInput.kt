package com.damiankwasniak.interview.widget

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.damiankwasniak.interview.R
import com.damiankwasniak.interview.watcher.CodeWatcher
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.childrenRecursiveSequence
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.textColor

class CodeInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), AnkoLogger {

    private val watcher = CodeWatcher {
        onCodeChanged {
            showError(false)
            code = it
            if (code.length == CODE_LENGTH) {
                onCodeEntered()
            } else {
                onCodeChanged()
            }
        }
    }

    private var onCodeEnteredListener: ((String) -> Unit) = {}

    private var onCodeChangedListener: ((String) -> Unit) = {}

    private fun onCodeEntered() {
        onCodeEnteredListener.invoke(code)
    }

    private fun onCodeChanged() {
        onCodeChangedListener.invoke(code)
    }

    init {
        View.inflate(context, R.layout.view_code_input, this)
    }

    var code: String = ""
        private set

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onInputFields { watcher.register(it) }

        handler.postDelayed({
            childrenRecursiveSequence().filterIsInstance<EditText>().first().let { v ->
                v.requestFocus()
                context?.inputMethodManager?.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
            }
        }, 250L)
    }

    private fun showError(show: Boolean) {
        childrenRecursiveSequence()
            .filterIsInstance<EditText>()
            .forEach { view ->
                if (show) {
                    view.textColor = ContextCompat.getColor(context, R.color.bright_pink)
                    view.backgroundTintList = getColorStateList(R.color.bright_pink_selector)
                    val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
                    view.startAnimation(shake)
                } else {
                    view.textColor = ContextCompat.getColor(context, R.color.black)
                    view.backgroundTintList = getColorStateList(R.color.black_selector)
                }
            }
    }

    private fun getColorStateList(color: Int): ColorStateList {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resources.getColorStateList(color, context.theme)
        } else {
            resources.getColorStateList(color)
        }
    }


    private fun onInputFields(f: (EditText) -> Unit) {
        childrenRecursiveSequence()
            .filterIsInstance<EditText>()
            .forEach(f)
    }

    fun onCodeEnteredListener(function: (code: String) -> Unit) {
        onCodeEnteredListener = function
    }

    fun onCodeChangedListener(function: (code: String) -> Unit) {
        onCodeChangedListener = function
    }

    companion object {
        private const val CODE_LENGTH = 4
    }
}