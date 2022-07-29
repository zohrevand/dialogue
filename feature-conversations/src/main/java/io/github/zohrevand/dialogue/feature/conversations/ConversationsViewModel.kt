package io.github.zohrevand.dialogue.feature.conversations

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.zohrevand.core.model.data.Conversation
import io.github.zohrevand.dialogue.core.data.repository.ConversationsRepository
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(

) : ViewModel() {

}

sealed interface ConversationsUiState {
    object Loading

    data class Success(val conversations: List<Conversation>)
}
