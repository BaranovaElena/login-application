package com.example.loginapplication.ui

class LoginContract {
    enum class Error {
        NULL_ERROR, EXIST_ERROR, NOT_EXIST_ERROR, NULL_LOGIN_ERROR
    }
    enum class Success {
        SIGN_IN_SUCCESS, SIGN_UP_SUCCESS, NEW_PASSWORD_SUCCESS
    }
    sealed class LoadState {
        object Loading : LoadState()
        class Success(var type: LoginContract.Success) : LoadState()
        class Error(var type: LoginContract.Error) : LoadState()
    }

    interface View {
        fun setState(state: LoadState)
        fun showPasswordError(isError: Boolean, length: Int)
        fun showLoginError(isError: Boolean)
    }

    interface Presenter {
        fun onAttach(view: View)
        fun onDetach()
        fun onSignIn(login: String?, password: String?)
        fun onSignUp(login: String?, password: String?)
        fun onForgotPassword(login: String?)
        fun onLoginTextChanged(login: String?)
        fun onPasswordTextChanged(password: String?)
    }
}