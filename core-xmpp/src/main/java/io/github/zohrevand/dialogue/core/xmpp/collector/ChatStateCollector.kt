package io.github.zohrevand.dialogue.core.xmpp.collector

import io.github.zohrevand.core.model.data.SendingChatState

interface ChatStateCollector {

    suspend fun collectChatState(onChatStateChanged: suspend (SendingChatState) -> Unit)
}