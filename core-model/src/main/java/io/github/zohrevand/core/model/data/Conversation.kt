package io.github.zohrevand.core.model.data

data class Conversation(
    val peerJid: String,
    val status: ConversationStatus,
    val draftMessage: String? = null,
    val lastMessage: Message? = null,
    val unreadMessagesCount: Int = 0,
    val chatState: ChatState = ChatState.Idle,
    val isChatOpen: Boolean = false
)

val Conversation.peerLocalPart: String
    get() = peerJid.substringBefore("@")

val Conversation.firstLetter: String
    get() = peerJid.take(1).uppercase()

val Conversation.subtitle: String?
    get() = if (draftMessage != null) {
        "Draft: $draftMessage"
    } else lastMessage?.body