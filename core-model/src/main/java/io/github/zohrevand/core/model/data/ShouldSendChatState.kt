package io.github.zohrevand.core.model.data

data class ShouldSendChatState(
    val chatState: ChatState,
    val consumed: Boolean
)
