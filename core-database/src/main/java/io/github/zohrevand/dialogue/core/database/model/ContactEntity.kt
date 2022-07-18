package io.github.zohrevand.dialogue.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.zohrevand.core.model.data.Contact
import io.github.zohrevand.core.model.data.Presence
import kotlinx.datetime.Instant

@Entity
data class ContactEntity(
    @PrimaryKey
    val jid: String,
    @ColumnInfo(name = "presence_type")
    val presenceType: Presence.Type,
    @ColumnInfo(name = "presence_mode")
    val presenceMode: Presence.Mode,
    @ColumnInfo(name = "presence_status")
    val presenceStatus: String,
    @ColumnInfo(name = "presence_priority")
    val presencePriority: Int,
    @ColumnInfo(name = "last_time")
    val lastTime: Instant
)

fun ContactEntity.asExternalModel() = Contact(
    jid = jid,
    presence = Presence(
        type = presenceType,
        mode = presenceMode,
        status = presenceStatus,
        priority = presencePriority
    ),
    lastTime = lastTime
)
