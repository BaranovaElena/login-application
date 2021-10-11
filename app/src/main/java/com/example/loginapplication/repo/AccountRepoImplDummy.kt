package com.example.loginapplication.repo

import android.os.Handler
import android.os.Looper
import com.example.loginapplication.domain.AccountEntity

private const val DELAY_MILLIS = 1000L

class AccountRepoImplDummy : AccountRepo {
    private var accountList: MutableList<AccountEntity> = mutableListOf(
        AccountEntity("lena@mail.ru", "121212"),
        AccountEntity("liza@gmail.com", "232323")
    )

    override fun checkAccountExists(account: AccountEntity, listener: AccountExistListener) {
        Handler(Looper.getMainLooper()).postDelayed({
            checkAccount(account, listener)
        }, DELAY_MILLIS)
    }

    private fun checkAccount(account: AccountEntity, listener: AccountExistListener) {
        if (accountList.contains(account))
            listener.onExist()
        else listener.onNotExist()
    }

    override fun saveAccount(account: AccountEntity, listener: AccountSaveListener) {
        Handler(Looper.getMainLooper()).postDelayed({
            addNewAccount(account, listener)
        }, DELAY_MILLIS)
    }

    private fun addNewAccount(account: AccountEntity, listener: AccountSaveListener) {
        if (!accountList.contains(account)) {
            accountList.add(account)
            listener.onSaved()
        }
    }

    override fun checkLoginExists(login: String, listener: AccountExistListener) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (isLoginExists(login)) {
                listener.onExist()
            } else listener.onNotExist()
        }, DELAY_MILLIS)
    }

    private fun isLoginExists(login: String): Boolean {
        for (account in accountList) {
            if (account.login == login) {
                return true
            }
        }
        return false
    }
}