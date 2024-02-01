package ru.klauz42.yetanotheronlinestore.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.databinding.ItemUserInfoBinding

class UserProfileView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: ItemUserInfoBinding

    var userName: String? = null
        set(value) {
            field = value
            binding.userName.text = value
        }

    var userPhoneNumber: String? = null
        set(value) {
            val number = value?.let {
                if (isPhoneNumberWellFormatted(it)) {
                    it
                } else if (isPhoneNumberCorrect(it)) {
                    wellFormatPhoneNumber(it)
                } else {
                    ""
                }
            }
            field = number
            binding.userPhoneNumber.text = number
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.item_user_info, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.UserProfileView, 0, 0)
            val name = typedArray.getString(R.styleable.UserProfileView_userName)
            val number = typedArray.getString(R.styleable.UserProfileView_userPhoneNumber)
            userName = name
            userPhoneNumber = number
            typedArray.recycle()
        }
    }

    fun setOnLogOutListener(listener: OnClickListener) {
        binding.buttonLogout.setOnClickListener(listener)
    }

    private fun isPhoneNumberWellFormatted(number: String): Boolean {
        val pattern1 = Regex("^\\+7 \\d{3} \\d{3}-\\d{2}-\\d{2}\$")
        val pattern2 = Regex("^\\+7 \\d{3} \\d{3} \\d{2} \\d{2}\$")
        return pattern1.matches(number) || pattern2.matches(number)
    }

    private fun isPhoneNumberCorrect(number: String): Boolean {
        val pattern = Regex("^\\+7\\d{10}\$")
        return pattern.matches(number)
    }

    private fun wellFormatPhoneNumber(correctNumber: String): String {
        return String.format(
            "+%s %s %s-%s-%s",
            correctNumber.substring(1, 2),
            correctNumber.substring(2, 5),
            correctNumber.substring(5, 8),
            correctNumber.substring(8, 10),
            correctNumber.substring(10, 12)
        )
    }
}