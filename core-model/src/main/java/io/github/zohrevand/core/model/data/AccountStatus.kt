package io.github.zohrevand.core.model.data

import io.github.zohrevand.core.model.data.AccountStatus.Disabled
import io.github.zohrevand.core.model.data.AccountStatus.Offline
import io.github.zohrevand.core.model.data.AccountStatus.Online

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

val AccountStatus.alreadyLoggedIn: Boolean
    get() = this == Online ||
        this == Disabled ||
        this == Offline
