package io.github.zohrevand.core.model.data

data class SendingChatState(
    val chatState: ChatState,
    val consumed: Boolean = false
)
