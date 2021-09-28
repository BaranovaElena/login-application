package com.example.loginapplication.repo

import com.example.loginapplication.domain.AccountEntity

interface AccountRepo {
    fun isAccountExists(account: AccountEntity) : Boolean
    fun saveAccount(account: AccountEntity)
    fun isLoginExists(login: String) : Boolean
}