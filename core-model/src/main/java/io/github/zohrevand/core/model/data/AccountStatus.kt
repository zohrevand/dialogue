package io.github.zohrevand.core.model.data

enum class AccountStatus {
    NotSet,
    ShouldRegister,
    Registering,
    ShouldLogin,
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

