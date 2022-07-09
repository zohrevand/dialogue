package io.github.zohrevand.core.model.data

enum class AccountStatus {
    PreRegistering,
    Registering,
    PreLoggingIn,
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