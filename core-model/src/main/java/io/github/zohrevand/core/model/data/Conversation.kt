package io.github.zohrevand.core.model.data

data class Conversation(
    val peerJid: String,
    val status: ConversationStatus,
    val draftMessage: String? = null,
    val lastMessage: Message? = null,
    val unreadMessagesCount: Int = 0,
    val chatState: ChatState = ChatState.Idle
)
