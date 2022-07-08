package io.github.zohrevand.dialogue.core.data.repository

import io.github.zohrevand.core.model.data.Account
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    /**
     * Gets the accounts as a stream
     * */
    fun getAccountsStream(): Flow<List<Account>>

    /**
     * Gets the specific account by id
     * */
    fun getAccount(id: Long): Flow<Account>

    /*
    * adds the provided account
    * */
    suspend fun addAccount(account: Account)

    /*
    * updates the provided account
    * */
    suspend fun updateAccount(account: Account)
}