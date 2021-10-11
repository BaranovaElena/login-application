package com.example.loginapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.example.loginapplication.R
import com.example.loginapplication.databinding.ActivityMainBinding
import com.example.loginapplication.ui.LoginContract.Success
import com.example.loginapplication.ui.LoginContract.Error
import com.example.loginapplication.ui.LoginContract.LoadState

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityMainBinding

    private val presenter: LoginContract.Presenter = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        presenter.onAttach(this)
        initButtonsListeners()
        initTextFieldsListeners()
    }

    private fun initButtonsListeners() {
        setSignInListener()
        setSignUpListener()
        binding.forgotPasswordButton.setOnClickListener {
            presenter.onForgotPassword(binding.loginInputEditText.text.toString())
        }
    }

    private fun setSignInListener() {
        binding.signInButton.setOnClickListener {
            presenter.onSignIn(
                binding.loginInputEditText.text.toString(),
                binding.passwordInputEditText.text.toString()
            )
        }
    }

    private fun setSignUpListener() {
        binding.signUpButton.setOnClickListener {
            presenter.onSignUp(
                binding.loginInputEditText.text.toString(),
                binding.passwordInputEditText.text.toString()
            )
        }
    }

    private fun initTextFieldsListeners() {
        binding.loginInputEditText.addTextChangedListener {
            presenter.onLoginTextChanged(it.toString())
        }
        binding.passwordInputEditText.addTextChangedListener {
            presenter.onPasswordTextChanged(it.toString())
        }
    }

    override fun setState(state: LoadState) {
        prepareToSetState()
        setViewWithState(state)
    }

    private fun setViewWithState(state: LoadState) {
        when (state) {
            is LoadState.Loading -> binding.loginProgressBar.visibility = View.VISIBLE
            is LoadState.Success -> setSuccessState(state.type)
            is LoadState.Error -> setErrorState(state.type)
        }
    }

    private fun setErrorState(error: Error) {
        when (error) {
            Error.NULL_ERROR -> showState(R.string.login_state_error_null)
            Error.NOT_EXIST_ERROR -> showState(R.string.login_state_error_not_exist)
            Error.EXIST_ERROR -> showState(R.string.login_state_error_exist)
            Error.NULL_LOGIN_ERROR -> showState(R.string.forgot_password_no_email)
        }
    }

    private fun showState(textId: Int) {
        binding.stateTextView.text = getString(textId)
    }

    private fun setSuccessState(success: Success) {
        binding.stateTextView.setTextColor(getColor(R.color.success_text_color))
        showSuccess(success)
    }

    private fun showSuccess(success: Success) {
        when (success) {
            Success.SIGN_IN_SUCCESS -> showState(R.string.sign_in_state_success)
            Success.SIGN_UP_SUCCESS -> showState(R.string.sign_up_state_success)
            Success.NEW_PASSWORD_SUCCESS -> showState(R.string.forgot_password_ok_response)
        }
    }

    private fun prepareToSetState() {
        binding.apply {
            loginProgressBar.visibility = View.GONE
            stateTextView.setTextColor(getColor(R.color.error_text_color))
            stateTextView.text = null
        }
    }

    override fun showPasswordError(isError: Boolean, length: Int) {
        if (isError) {
            binding.passwordTextInputLayout.error = "${getString(R.string.password_not_valid)} $length"
        } else {
            binding.passwordTextInputLayout.error = null
        }
    }

    override fun showLoginError(isError: Boolean) {
        if (isError) {
            binding.loginTextInputLayout.error = getString(R.string.login_not_valid)
        } else {
            binding.loginTextInputLayout.error = null
        }
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}