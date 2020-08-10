package com.damiankwasniak.interview.watcher

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.inputMethodManager

class CodeWatcher(initializer: CodeWatcher.() -> Unit): AnkoLogger {

    init {
        initializer()
    }

    private val inputs: MutableList<Input> = mutableListOf()

    private var onCodeChanged: ((String) -> Unit)? = null

    var focusHolder: View? = null

    fun register(v: EditText) {
        val watcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s == null) return
                afterTextChanged(v, s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        inputs.add(Input(v, watcher))
    }

    private fun afterTextChanged(view: EditText, editable: Editable) {
        val idx = inputs.indexOfFirst { it.editText == view}

        val nextIdx = when (editable.isEmpty()) {
            true -> idx - 1
            false -> idx + 1
        }

        if (nextIdx in 0 until inputs.size) {
            inputs[nextIdx].requestFocus()
        } else {
            view.clearFocus()
            view.context.inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        val text = inputs.joinToString(separator = "") { it.string }
        onCodeChanged?.invoke(text)
    }

    fun reset() {
        inputs.forEach { it.reset() }
        focusHolder?.requestFocus()
    }

    fun clear() {
        inputs.forEach { it.destroy() }
        inputs.clear()
    }

    fun onCodeChanged(f: (String) -> Unit) {
        onCodeChanged = f
    }

    private inner class Input(val editText: EditText, private val textWatcher: TextWatcher) {

        init {
            editText.addTextChangedListener(textWatcher)
        }

        fun requestFocus() {
            editText.requestFocus()
        }

        fun reset() {
            destroy()
            editText.setText("")
            editText.addTextChangedListener(textWatcher)
        }

        fun destroy() {
            editText.removeTextChangedListener(textWatcher)
        }

        val string: String get() = editText.text.toString()

        val hasText: Boolean get() = string.isNotEmpty()
    }
}
