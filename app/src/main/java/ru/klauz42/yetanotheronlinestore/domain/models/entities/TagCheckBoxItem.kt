package ru.klauz42.yetanotheronlinestore.domain.models.entities

data class TagCheckBoxItem(
    val id: FilterTag,
    val text: String = "",
    var isSelected: Boolean = false
)