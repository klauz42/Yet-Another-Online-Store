package ru.klauz42.yetanotheronlinestore.presentation

fun getPluralFormText(count: Int, form1: String, form2: String, form5: String): String {
    var c = count % 100
    if (c in 11..19)
        return form5.format(c)

    c %= 10
    return when (c) {
        1 -> form1.format(c)
        2, 3, 4 -> form2.format(c)
        else -> form5.format(c)
    }
}