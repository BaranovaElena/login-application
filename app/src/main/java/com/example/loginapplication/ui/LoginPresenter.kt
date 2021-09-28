package com.example.loginapplication.ui

import com.example.loginapplication.domain.AccountEntity
import com.example.loginapplication.repo.AccountRepo
import com.example.loginapplication.repo.AccountRepoImplDummy
import com.example.loginapplication.ui.LoginContract.Success
import com.example.loginapplication.ui.LoginContract.Error
import com.example.loginapplication.ui.LoginContract.LoadState

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
        } else if (!repo.isAccountExists(AccountEntity(login, password))) {
            view?.setState(LoadState.Error(Error.NOT_EXIST_ERROR))
        } else {
            view?.setState(LoadState.Success(Success.SIGN_IN_SUCCESS))
        }
    }

    override fun onSignUp(login: String?, password: String?) {
        view?.setState(LoadState.Loading)
        if (login.isNullOrEmpty() || password.isNullOrEmpty()) {
            view?.setState(LoadState.Error(Error.NULL_ERROR))
        } else if (repo.isAccountExists(AccountEntity(login, password))) {
            view?.setState(LoadState.Error(Error.EXIST_ERROR))
        } else {
            repo.saveAccount(AccountEntity(login, password))
            view?.setState(LoadState.Success(Success.SIGN_UP_SUCCESS))
        }
    }

    override fun onForgotPassword(login: String?) {
        view?.setState(LoadState.Loading)
        when {
            login.isNullOrEmpty() -> view?.setState(LoadState.Error(Error.NULL_LOGIN_ERROR))
            repo.isLoginExists(login) -> view?.setState(LoadState.Success(Success.NEW_PASSWORD_SUCCESS))
            else -> view?.setState(LoadState.Error(Error.NOT_EXIST_ERROR))
        }
    }

    override fun onLoginTextChanged(login: String?) {

    }

    override fun onPasswordTextChanged(password: String?) {
    }
}