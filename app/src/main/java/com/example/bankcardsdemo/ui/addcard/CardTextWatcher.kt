package com.example.bankcardsdemo.ui.addcard

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.bankcardsdemo.utils.Extensions.formatCreditCardNumber
import com.example.bankcardsdemo.utils.Extensions.formatExpirationDate

class CardTextWatcher(private val editText: EditText, private val type: Int) : TextWatcher {

    companion object {
        const val CARD_NUMBER = 1
        const val EXPIRATION_DATE = 2
    }

    private var previousText = ""

    override fun afterTextChanged(s: Editable?) {
        val text = s.toString()
        if (text == previousText) return

        val formatted = when (type) {
            CARD_NUMBER -> text.formatCreditCardNumber()
            else -> text.formatExpirationDate()
        }

        previousText = formatted
        editText.removeTextChangedListener(this)
        editText.setText(formatted)
        editText.setSelection(formatted.length)
        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // do nothing
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // do nothing
    }
}
