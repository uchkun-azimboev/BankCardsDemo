package com.example.bankcardsdemo.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

object Extensions {

    val Context.isConnected: Boolean
        get() = (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo?.isConnected == true

    fun Fragment.toast(msg: String?) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    fun Activity.toast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    infix fun View.click(click: () -> Unit) {
        setOnClickListener { click() }
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }

    fun String.removeSpaces(): String {
        return replace("\\s".toRegex(), "")
    }

    fun String.formatCreditCardNumber(): String {
        val formatted = StringBuilder()
        var nextIndex = 0
        var spaceCount = 0

        for (element in this) {
            if (Character.isDigit(element)) {
                if (nextIndex % 4 == 0 && formatted.isNotEmpty()) {
                    formatted.append(" ")
                    spaceCount++
                }
                formatted.append(element)
                nextIndex++
            }
        }

        return formatted.toString()
    }

    fun String.formatExpirationDate(): String {
        val formatted = StringBuilder()
        var nextIndex = 0

        for (element in this) {
            if (Character.isDigit(element)) {
                if (nextIndex == 2 && formatted.isNotEmpty()) {
                    formatted.append("/")
                }
                formatted.append(element)
                nextIndex++
            }
        }

        return formatted.toString()
    }
}