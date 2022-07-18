package io.github.zohrevand.core.model.data

data class Presence(
    val type: Type = Type.Available,
    val mode: Mode = Mode.Available,
    val status: String = "",
    val priority: Int = 0
) {
    enum class Type {
        /**
        * The user is available to receive messages (default).
        */
        Available,

        /**
         * The user is unavailable to receive messages.
         */
        Unavailable,

        /**
         * Request subscription to recipient's presence.
         */
        Subscribe,

        /**
         * Grant subscription to sender's presence.
         */
        Subscribed,

        /**
         * Request removal of subscription to sender's presence.
         */
        Unsubscribe,

        /**
         * Grant removal of subscription to sender's presence.
         */
        Unsubscribed,

        /**
         * The presence stanza contains an error message.
         */
        Error,

        /**
         * A presence probe as defined in section 4.3 of RFC 6121.
         */
        Probe
    }

    enum class Mode {
        /**
         * Free to chat.
         */
        Chat,

        /**
         * Available (the default).
         */
        Available,

        /**
         * Away.
         */
        Away,

        /**
         * Away for an extended period of time.
         */
        Xa,

        /**
         * Do not disturb.
         */
        Dnd
    }
}