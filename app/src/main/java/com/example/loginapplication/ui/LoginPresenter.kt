package com.example.loginapplication.ui

import com.example.loginapplication.domain.AccountEntity
import com.example.loginapplication.repo.AccountRepo
import com.example.loginapplication.repo.AccountRepoImplDummy

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
        view?.setState(LoginContract.LoadState.Loading)
        if (login == null || password == null) {
            view?.setState(LoginContract.LoadState.Error(LoginContract.Error.NULL_ERROR))
        } else if (!repo.isAccountExists(AccountEntity(login, password))) {
            view?.setState(LoginContract.LoadState.Error(LoginContract.Error.NOT_EXIST_ERROR))
        } else {
            view?.setState(LoginContract.LoadState.Success(LoginContract.Success.SIGN_IN_SUCCESS))
        }
    }

    override fun onSignUp(login: String?, password: String?) {
        TODO("Not yet implemented")
    }

    override fun onForgotPassword() {
        TODO("Not yet implemented")
    }

    override fun onLoginTextChanged(login: String) {
        TODO("Not yet implemented")
    }

    override fun onPasswordTextChanged(toString: String) {
        TODO("Not yet implemented")
    }
}