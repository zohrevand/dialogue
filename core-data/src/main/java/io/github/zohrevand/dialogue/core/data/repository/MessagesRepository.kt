package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Message
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {

    fun getMessage(id: String): Flow<Message>
}
