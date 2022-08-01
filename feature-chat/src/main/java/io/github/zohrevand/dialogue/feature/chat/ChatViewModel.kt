package io.github.zohrevand.dialogue.feature.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.core.model.data.ConversationStatus.NotStarted
import io.github.zohrevand.core.model.data.ConversationStatus.Started
import io.github.zohrevand.core.model.data.Message
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import io.github.zohrevand.dialogue.core.data.repository.MessagesRepository
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Loading
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Success
import io.github.zohrevand.dialogue.feature.chat.navigation.ChatDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    conversationsRepository: ConversationsRepository,
    private val messagesRepository: MessagesRepository
) : ViewModel() {

    private val contactId: String = checkNotNull(
        savedStateHandle[ChatDestination.contactJidArg]
    )

    private val conversation = conversationsRepository.getConversation(peerJid = contactId)

    private val messages = messagesRepository.getMessagesStream(peerJid = contactId)

    val uiState: StateFlow<ChatUiState> =
        combine(
            conversation,
            messages
        ) { conversation, messages ->
            if (conversation.status == NotStarted) {
                conversationsRepository.updateConversation(conversation.copy(status = Started))
                Loading
            } else {
                Success(conversation, messages)
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Loading
            )

    fun sendMessage(text: String) {
        viewModelScope.launch {
            messagesRepository.updateMessage(
                Message.create(text, contactId)
            )
        }
    }

    fun userTyping() {

    }
}

sealed interface ChatUiState {
    data class Success(val conversation: Conversation, val messages: List<Message>) : ChatUiState
    object Loading : ChatUiState
}
