package com.example.loginapplication.repo

import com.example.loginapplication.domain.AccountEntity

interface AccountRepo {
    fun checkAccountExists(account: AccountEntity, listener: AccountExistListener)
    fun saveAccount(account: AccountEntity, listener: AccountSaveListener)
    fun checkLoginExists(login: String, listener: AccountExistListener)
}

interface AccountExistListener {
    fun onExist()
    fun onNotExist()
}

interface AccountSaveListener{
    fun onSaved()
}