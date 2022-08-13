package io.github.zohrevand.dialogue.service.xmpp.collector

import io.github.zohrevand.core.model.data.SendingChatState

interface ChatStateCollector {

    suspend fun collectChatState(onChatStateChanged: suspend (SendingChatState) -> Unit)
}
