package io.github.zohrevand.core.model.data

enum class MessageStatus {
    PreSend,
    Sent,
    Received,
    SentFailed,
    SentDelivered,
    SentDisplayed,
    ReceivedDisplayed
}