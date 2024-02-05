package ru.klauz42.yetanotheronlinestore.domain.models.entities

enum class FilterTag {
    ALL, FACE, BODY, SUNTAN, MASK

}

fun FilterTag.getString(): String {
    return when (this) {
        FilterTag.ALL -> "all"
        FilterTag.FACE -> "face"
        FilterTag.BODY -> "body"
        FilterTag.SUNTAN -> "suntan"
        FilterTag.MASK -> "mask"
    }
}

