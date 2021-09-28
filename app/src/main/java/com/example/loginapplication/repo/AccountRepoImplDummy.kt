package com.example.loginapplication.repo

import com.example.loginapplication.domain.AccountEntity

class AccountRepoImplDummy : AccountRepo {
    private var accountList: MutableList<AccountEntity> = mutableListOf(
        AccountEntity("lena@mail.ru", "121212"),
        AccountEntity("liza@gmail.com", "232323")
    )

    override fun isAccountExists(account: AccountEntity): Boolean {
        return accountList.contains(account)
    }

    override fun saveAccount(account: AccountEntity) {
        if (!accountList.contains(account)) {
            accountList.add(account)
        }
    }

    override fun isLoginExists(login: String): Boolean {
        for (account in accountList) {
            if (account.login == login)
                return true
        }
        return false
    }
}