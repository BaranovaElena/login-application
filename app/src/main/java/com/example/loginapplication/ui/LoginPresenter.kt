package com.example.loginapplication.ui

import com.example.loginapplication.domain.AccountEntity
import com.example.loginapplication.repo.AccountRepo
import com.example.loginapplication.repo.AccountRepoImplDummy
import com.example.loginapplication.ui.LoginContract.Success
import com.example.loginapplication.ui.LoginContract.Error
import com.example.loginapplication.ui.LoginContract.LoadState

private const val PASSWORD_LENGTH_MIN = 6

class LoginPresenter : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private val repo: AccountRepo = AccountRepoImplDummy()

    override fun onAttach(view: LoginContract.View) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
    }

    override fun onSignIn(login: String?, password: String?) {
        view?.setState(LoadState.Loading)
        if (login.isNullOrEmpty() || password.isNullOrEmpty()) {
            view?.setState(LoadState.Error(Error.NULL_ERROR))
        } else checkValidSignIn(AccountEntity(login, password))
    }

    private fun checkValidSignIn(account: AccountEntity) {
        if (!repo.isAccountExists(account)) {
            view?.setState(LoadState.Error(Error.NOT_EXIST_ERROR))
        } else {
            view?.setState(LoadState.Success(Success.SIGN_IN_SUCCESS))
        }
    }

    override fun onSignUp(login: String?, password: String?) {
        view?.setState(LoadState.Loading)
        if (login.isNullOrEmpty() || password.isNullOrEmpty()) {
            view?.setState(LoadState.Error(Error.NULL_ERROR))
        } else checkValidSignUp(AccountEntity(login, password))
    }

    private fun checkValidSignUp(account: AccountEntity) {
        if (repo.isAccountExists(account)) {
            view?.setState(LoadState.Error(Error.EXIST_ERROR))
        } else signUpAccount(account)
    }

    private fun signUpAccount(account: AccountEntity) {
        repo.saveAccount(account)
        view?.setState(LoadState.Success(Success.SIGN_UP_SUCCESS))
    }

    override fun onForgotPassword(login: String?) {
        view?.setState(LoadState.Loading)
        if (login.isNullOrEmpty()) {
            view?.setState(LoadState.Error(Error.NULL_LOGIN_ERROR))
        } else checkLoginRegistered(login)
    }

    private fun checkLoginRegistered(login: String) {
        if (repo.isLoginExists(login)) {
            view?.setState(LoadState.Success(Success.NEW_PASSWORD_SUCCESS))
        } else {
            view?.setState(LoadState.Error(Error.NOT_EXIST_ERROR))
        }
    }

    override fun onLoginTextChanged(login: String?) {
        login?.let {
            checkLoginToValid(it)
        } ?: view?.showLoginError(true)
    }

    private fun checkLoginToValid(login: String) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
            view?.showLoginError(false)
        } else {
            view?.showLoginError(true)
        }
    }

    override fun onPasswordTextChanged(password: String?) {
        password?.let {
            checkPasswordToValid(it)
        } ?: view?.showPasswordError(true, PASSWORD_LENGTH_MIN)
    }

    private fun checkPasswordToValid(password: String) {
        if (password.length < PASSWORD_LENGTH_MIN) {
            view?.showPasswordError(true, PASSWORD_LENGTH_MIN)
        } else {
            view?.showPasswordError(false, PASSWORD_LENGTH_MIN)
        }
    }
}