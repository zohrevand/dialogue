package io.github.zohrevand.core.model.data

data class Account(
    val id: String,
    val username: String,
    val domain: String,
    val password: String,
    val displayName: String,
    val resource: String,
    val status: Status
) {
    enum class Status {
        Registering,
        LoggingIn,
        Disabled,
        Offline,
        Online,
        Unauthorized,
        ServerNotFound,
        RegistrationSuccessful,
        RegistrationFailed,
        RegistrationAlreadyExist
    }
}
