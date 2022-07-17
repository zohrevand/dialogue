package io.github.zohrevand.core.model.data

enum class AccountStatus {
    NotSet,
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