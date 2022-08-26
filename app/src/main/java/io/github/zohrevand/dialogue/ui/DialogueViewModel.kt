package io.github.zohrevand.dialogue.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.ChatState.Inactive
import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.core.model.data.MessageStatus
import io.github.zohrevand.core.model.data.SendingChatState
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import io.github.zohrevand.dialogue.core.data.repository.MessagesRepository
import io.github.zohrevand.dialogue.core.data.repository.PreferencesRepository
import io.github.zohrevand.dialogue.core.data.repository.SendingChatStatesRepository
import io.github.zohrevand.dialogue.ui.ConnectionStatusUiState.Connected
import io.github.zohrevand.dialogue.ui.ConnectionStatusUiState.Connecting
import io.github.zohrevand.dialogue.ui.ConnectionStatusUiState.Idle
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus

@HiltViewModel
class DialogueViewModel @Inject constructor(
    preferencesRepository: PreferencesRepository,
    private val conversationsRepository: ConversationsRepository,
    private val sendingChatStatesRepository: SendingChatStatesRepository,
    private val messageRepository: MessagesRepository
) : ViewModel() {

    init {
        viewModelScope.launch { generateFakeData() }
    }

    val connectionStatusUiState: StateFlow<ConnectionStatusUiState> =
        preferencesRepository.getConnectionStatus()
            .map { connectionStatus ->
                if (connectionStatus.availability && connectionStatus.authenticated) {
                    Connected
                } else {
                    // TODO: navigate to auth screen if connection available but not authenticated
                    Connecting
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Idle
            )

    fun onExitChat(contactId: String) {
        viewModelScope.launch {
            closeConversation(contactId)
            resetChatState(contactId)
        }
    }

    private suspend fun closeConversation(contactId: String) {
        conversationsRepository.updateConversation(
            peerJid = contactId,
            isChatOpen = false
        )
    }

    private suspend fun resetChatState(contactId: String) {
        sendingChatStatesRepository.updateSendingChatState(
            SendingChatState(peerJid = contactId, chatState = Inactive)
        )
    }

    private suspend fun generateFakeData() {
        val contact1 = Contact.create(jid = "mary@mim.im")
        val contact2 = Contact.create(jid = "john@mim.im")
        val contact3 = Contact.create(jid = "niki@mim.im")
        val contact4 = Contact.create(jid = "ian@mim.im")
        val contact5 = Contact.create(jid = "parsa@mim.im")
        val contact6 = Contact.create(jid = "bob@mim.im")
        val contact7 = Contact.create(jid = "adam@mim.im")
        val contact8 = Contact.create(jid = "goerge@mim.im")
        val contact9 = Contact.create(jid = "nick@mim.im")

        val now = Clock.System.now()

        val message1100 = Message(
            id = 1100,
            stanzaId = UUID.randomUUID().toString(),
            body = "Hello Mary, are you coming for dinner?",
            peerJid = contact1.jid,
            sendTime = now.minus(20, DateTimeUnit.MINUTE),
            status = MessageStatus.SentDelivered
        )
        val message1099 = Message(
            id = 1099,
            stanzaId = UUID.randomUUID().toString(),
            body = "Hi, how are you? Yes sure.",
            peerJid = contact1.jid,
            sendTime = now.minus(20, DateTimeUnit.MINUTE),
            status = MessageStatus.Received
        )

        val messages = listOf(
            message1099,
            message1100
        )

        messages.forEach { messageRepository.addMessage(it) }

        val conversation1 = Conversation.createNewConversation(peerJid = contact1.jid)
            .copy(lastMessage = message1099, unreadMessagesCount = 2)
        val conversation2 = Conversation.createNewConversation(peerJid = contact2.jid)
            .copy(lastMessage = message1099, unreadMessagesCount = 1)
        val conversation3 = Conversation.createNewConversation(peerJid = contact3.jid)
            .copy(lastMessage = message1099, unreadMessagesCount = 0)
        val conversation4 = Conversation.createNewConversation(peerJid = contact4.jid)
            .copy(lastMessage = message1099, unreadMessagesCount = 12)
        val conversation5 = Conversation.createNewConversation(peerJid = contact5.jid)
            .copy(lastMessage = message1099, unreadMessagesCount = 0)
        val conversation6 = Conversation.createNewConversation(peerJid = contact6.jid)
            .copy(lastMessage = message1099, unreadMessagesCount = 0)
        val conversation7 = Conversation.createNewConversation(peerJid = contact7.jid)
            .copy(lastMessage = message1099, unreadMessagesCount = 0)
        val conversation8 = Conversation.createNewConversation(peerJid = contact8.jid)
            .copy(lastMessage = message1099, unreadMessagesCount = 0)
        val conversation9 = Conversation.createNewConversation(peerJid = contact9.jid)
            .copy(lastMessage = message1099, unreadMessagesCount = 0)

        val conversations = listOf(
            conversation1,
            conversation2,
            conversation3,
            conversation4,
            conversation5,
            conversation6,
            conversation7,
            conversation8,
            conversation9
        )

        conversations.forEach { conversationsRepository.addConversation(it) }
    }
}

sealed interface ConnectionStatusUiState {
    object Idle : ConnectionStatusUiState

    object Connected : ConnectionStatusUiState

    object Connecting : ConnectionStatusUiState
}
