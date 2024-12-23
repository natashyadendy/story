package com.example.submissiondua.pref

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.submissiondua.R

class SecurePasswordEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        initializeTextWatcher()
    }

    private fun initializeTextWatcher() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    checkPasswordValidity(it)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun checkPasswordValidity(password: CharSequence) {
        val validationError = when {
            password.isBlank() -> context.getString(R.string.password_must_not_be_empty)
            password.length < 8 -> context.getString(R.string.password_8_characters)
            else -> null
        }
        error = validationError
    }
}
