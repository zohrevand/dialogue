package io.github.zohrevand.core.model.data

data class SendingChatState(
    val id: Long? = null,
    val peerJid: String,
    val chatState: ChatState,
    val consumed: Boolean = false
)
