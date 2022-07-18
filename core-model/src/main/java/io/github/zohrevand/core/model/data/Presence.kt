package io.github.zohrevand.core.model.data

data class Presence(
    val type: Type,
    val mode: Mode,
    val status: String = "",
    val priority: Int = 0
) {
    enum class Type {

    }

    enum class Mode {

    }
}