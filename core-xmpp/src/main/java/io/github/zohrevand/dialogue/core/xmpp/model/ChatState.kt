package io.github.zohrevand.dialogue.core.xmpp.model

import io.github.zohrevand.core.model.data.ChatState
import io.github.zohrevand.core.model.data.ChatState.Active
import io.github.zohrevand.core.model.data.ChatState.Composing
import io.github.zohrevand.core.model.data.ChatState.Gone
import io.github.zohrevand.core.model.data.ChatState.Idle
import io.github.zohrevand.core.model.data.ChatState.Inactive
import io.github.zohrevand.core.model.data.ChatState.Paused
import org.jivesoftware.smackx.chatstates.ChatState.active
import org.jivesoftware.smackx.chatstates.ChatState.composing
import org.jivesoftware.smackx.chatstates.ChatState.gone
import org.jivesoftware.smackx.chatstates.ChatState.inactive
import org.jivesoftware.smackx.chatstates.ChatState.paused
import org.jivesoftware.smackx.chatstates.ChatState as SmackChatState

fun SmackChatState.asExternalEnum() = when (this) {
    active -> Active
    inactive -> Inactive
    composing -> Composing
    paused -> Paused
    gone -> Gone
}

fun ChatState.asSmackEnum() = when (this) {
    Active -> active
    Inactive, Idle -> inactive
    Composing -> composing
    Paused -> paused
    Gone -> gone
}