package com.example.loginapplication.ui

class LoginContract {
    enum class Error {
        NULL_ERROR, EXIST_ERROR, NOT_EXIST_ERROR
    }
    enum class Success {
        SIGN_IN_SUCCESS, SIGN_UP_SUCCESS
    }
    sealed class LoadState {
        object Loading : LoadState()
        class Success(var type: LoginContract.Success) : LoadState()
        class Error(var type: LoginContract.Error) : LoadState()
    }

    interface View {
        fun setState(state: LoadState)
    }

    interface Presenter {
        fun onAttach(view: View)
        fun onDetach()
        fun onSignIn(login: String?, password: String?)
        fun onSignUp(login: String?, password: String?)
        fun onForgotPassword()
        fun onLoginTextChanged(login: String)
        fun onPasswordTextChanged(toString: String)
    }
}