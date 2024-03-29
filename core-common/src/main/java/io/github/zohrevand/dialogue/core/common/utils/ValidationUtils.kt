package io.github.zohrevand.dialogue.core.common.utils

import android.text.TextUtils

val String.isValidJid: Boolean
    get() = !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
