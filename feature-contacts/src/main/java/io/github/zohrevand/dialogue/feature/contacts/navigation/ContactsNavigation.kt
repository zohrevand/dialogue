package io.github.zohrevand.dialogue.feature.contacts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.zohrevand.dialogue.core.navigation.DialogueNavigationDestination
import io.github.zohrevand.dialogue.feature.contacts.ContactsRoute

object ContactsDestination : DialogueNavigationDestination {
    override val route = "contacts_route"
    override val destination = "contacts_destination"
}

fun NavGraphBuilder.contactsGraph() {
    composable(
        route = ContactsDestination.route,
    ) {
        ContactsRoute()
    }
}
