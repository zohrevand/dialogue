package io.github.zohrevand.dialogue.feature.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.ChatState
import io.github.zohrevand.core.model.data.ChatState.Composing
import io.github.zohrevand.core.model.data.ChatState.Paused
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus.NotStarted
import io.github.zohrevand.core.model.data.ConversationStatus.Started
import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.core.model.data.SendingChatState
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import io.github.zohrevand.dialogue.core.data.repository.MessagesRepository
import io.github.zohrevand.dialogue.core.data.repository.SendingChatStatesRepository
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Loading
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Success
import io.github.zohrevand.dialogue.feature.chat.navigation.ChatDestination
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val conversationsRepository: ConversationsRepository,
    private val messagesRepository: MessagesRepository,
    private val sendingChatStatesRepository: SendingChatStatesRepository
) : ViewModel() {

    private val contactId: String = checkNotNull(
        savedStateHandle[ChatDestination.contactJidArg]
    )

    private val conversation = conversationsRepository.getConversation(peerJid = contactId)

    private val messages = messagesRepository.getMessagesStream(peerJid = contactId)

    private var typeJob: Job? = null
    private var currentChatState: ChatState? = null

    val uiState: StateFlow<ChatUiState> =
        combine(
            conversation,
            messages
        ) { conversation, messages ->
            conversation?.let {
                if (conversation.status == NotStarted) {
                    conversationsRepository.updateConversation(conversation.copy(status = Started))
                    Loading
                } else {
                    Success(conversation, messages)
                }
            } ?: run { Loading }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Loading
            )

    fun sendMessage(text: String) {
        typeJob?.cancel()
        viewModelScope.launch {
            messagesRepository.updateMessage(
                Message.create(text, contactId)
            )

            removeDraft()
        }
    }

    fun userTyping(messageText: String) {
        updateDraft(messageText)

        typeJob?.cancel()
        typeJob = viewModelScope.launch {
            delay(3_000)
            sendingChatStatesRepository.updateSendingChatState(
                SendingChatState(peerJid = contactId, chatState = Paused)
            )
            currentChatState = Paused
        }

        if (currentChatState != Composing) {
            currentChatState = Composing
            viewModelScope.launch {
                sendingChatStatesRepository.updateSendingChatState(
                    SendingChatState(peerJid = contactId, chatState = Composing)
                )
            }
        }
    }

    private fun updateDraft(messageText: String) {
        viewModelScope.launch {
            conversation.first()?.let {
                conversationsRepository.updateConversation(it.copy(draftMessage = messageText))
            }
        }
    }

    private suspend fun removeDraft() {
        conversation.first()?.let {
            conversationsRepository.updateConversation(it.copy(draftMessage = null))
        }
    }
}

sealed interface ChatUiState {
    data class Success(val conversation: Conversation, val messages: List<Message>) : ChatUiState
    object Loading : ChatUiState
}
