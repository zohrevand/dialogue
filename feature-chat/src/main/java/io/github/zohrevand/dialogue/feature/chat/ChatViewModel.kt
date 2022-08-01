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
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import io.github.zohrevand.dialogue.core.data.repository.MessagesRepository
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Loading
import io.github.zohrevand.dialogue.feature.chat.ChatUiState.Success
import io.github.zohrevand.dialogue.feature.chat.navigation.ChatDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val conversationsRepository: ConversationsRepository,
    private val messagesRepository: MessagesRepository
) : ViewModel() {

    private val contactId: String = checkNotNull(
        savedStateHandle[ChatDestination.contactJidArg]
    )

    private val conversation = conversationsRepository.getConversation(peerJid = contactId)

    private val messages = messagesRepository.getMessagesStream(peerJid = contactId)

    private var inputState = InputState()

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

    // TODO: refactor to better idiomatic solution
    fun userTyping() {
        if (inputState.lastTimeTyped == null) {
            viewModelScope.launch {
                // 3 second delay to check if user is still typing
                delay(3_000)
                if (inputState.userStoppedTyping) {
                    inputState = inputState.copy(chatState = Paused, lastTimeTyped = null)
                    conversationsRepository.updateConversation(
                        conversation.first().copy(chatState = Paused)
                    )
                }
            }
        }

        inputState = inputState.copy(chatState = Composing, lastTimeTyped = currentTimeMillis())

        if (inputState.chatState != Composing) {
            viewModelScope.launch {
                conversationsRepository.updateConversation(
                    conversation.first().copy(chatState = Composing)
                )
            }
        }
    }
}

data class InputState(
    val chatState: ChatState = ChatState.Active,
    val lastTimeTyped: Long? = null
) {
    val userStoppedTyping: Boolean
        get() = lastTimeTyped != null && currentTimeMillis() - lastTimeTyped > 3_000
}

sealed interface ChatUiState {
    data class Success(val conversation: Conversation, val messages: List<Message>) : ChatUiState
    object Loading : ChatUiState
}
