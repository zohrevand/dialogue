package io.github.zohrevand.core.model.data

data class SendingChatState(
    val id: Long? = null,
    val chatState: ChatState,
    val consumed: Boolean = false
)
