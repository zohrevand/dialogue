package io.github.zohrevand.dialogue.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactEntity(
    @PrimaryKey
    val jid: String
)
