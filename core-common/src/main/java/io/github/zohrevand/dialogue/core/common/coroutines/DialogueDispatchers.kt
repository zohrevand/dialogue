package io.github.zohrevand.dialogue.core.common.coroutines

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dialogueDispatcher: DialogueDispatchers)

enum class DialogueDispatchers {
    IO
}
