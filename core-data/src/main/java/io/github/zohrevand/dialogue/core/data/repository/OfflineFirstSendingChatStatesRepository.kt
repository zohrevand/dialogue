package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.dialogue.core.database.dao.SendingChatStateDao
import javax.inject.Inject

class OfflineFirstSendingChatStatesRepository @Inject constructor(
    private val sendingChatStateDao: SendingChatStateDao
) : SendingChatStatesRepository {

}