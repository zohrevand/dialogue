package io.github.zohrevand.core.model.data

enum class MessageStatus {
    ShouldSend,
    Sent,
    Received,
    SentFailed,
    SentDelivered,
    SentDisplayed,
    ReceivedDisplayed
}