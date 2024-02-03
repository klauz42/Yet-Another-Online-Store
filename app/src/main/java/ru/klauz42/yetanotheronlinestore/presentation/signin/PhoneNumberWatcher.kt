package ru.klauz42.yetanotheronlinestore.presentation.signin

import android.text.Editable
import android.text.TextWatcher


class PhoneNumberWatcher(val rawPhoneNumberCallback: (String) -> Unit) : TextWatcher {
    var isFormatting = false
    var lastFormattedText: String? = null

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) return
        isFormatting = true

        s?.let {
            var phoneNumber =
                s.toString().replace(Regex("[^\\d]"), "") // Удаление всех нецифровых символов

            if (phoneNumber.startsWith("8")) {
                phoneNumber = "7${phoneNumber.substring(1)}"
            } else if (!phoneNumber.startsWith("7")) {
                phoneNumber = "7$phoneNumber"
            }

            if (phoneNumber.length > 1) {
                phoneNumber = "+$phoneNumber"
            }

            rawPhoneNumberCallback(phoneNumber)

            val tmpStringBuilder = StringBuilder(16)
            phoneNumber.forEachIndexed { index, c ->
                when (index) {
                    2, 5, 8, 10 -> tmpStringBuilder.append(" $c")
                    else -> tmpStringBuilder.append(c)
                }
            }
            val tmp = tmpStringBuilder.toString()

            if (tmp != lastFormattedText) {
                s.replace(0, s.length, tmp, 0, tmp.length)
                lastFormattedText = tmp
            }
        }

        isFormatting = false
    }

}