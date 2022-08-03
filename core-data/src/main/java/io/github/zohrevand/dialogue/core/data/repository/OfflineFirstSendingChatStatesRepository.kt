package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.SendingChatState
import io.github.zohrevand.dialogue.core.data.model.asEntity
import io.github.zohrevand.dialogue.core.database.dao.SendingChatStateDao
import io.github.zohrevand.dialogue.core.database.model.SendingChatStateEntity
import io.github.zohrevand.dialogue.core.database.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstSendingChatStatesRepository @Inject constructor(
    private val sendingChatStateDao: SendingChatStateDao
) : SendingChatStatesRepository {

    override fun getSendingChatStatesStream(): Flow<List<SendingChatState>> =
        sendingChatStateDao.getSendingChatStateEntitiesStream()
            .map { it.map(SendingChatStateEntity::asExternalModel) }

    override suspend fun updateSendingChatState(sendingChatState: SendingChatState) =
        sendingChatStateDao.upsert(sendingChatState.asEntity())
}