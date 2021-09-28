package com.example.loginapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.example.loginapplication.R
import com.example.loginapplication.databinding.ActivityMainBinding

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
        binding.forgotPasswordButton.setOnClickListener { presenter.onForgotPassword() }
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

    override fun setState(state: LoginContract.LoadState) {
        binding.loginProgressBar.visibility = View.GONE
        binding.stateTextView.setTextColor(getColor(R.color.error_text_color))
        binding.stateTextView.text = null

        when (state) {
            is LoginContract.LoadState.Loading -> {
                binding.loginProgressBar.visibility = View.VISIBLE
            }
            is LoginContract.LoadState.Success -> {
                binding.stateTextView.setTextColor(getColor(R.color.success_text_color))
                when (state.type) {
                    LoginContract.Success.SIGN_IN_SUCCESS ->
                        binding.stateTextView.text = getString(R.string.sign_in_state_success)
                    LoginContract.Success.SIGN_UP_SUCCESS ->
                        binding.stateTextView.text = getString(R.string.sign_up_state_success)
                }
            }
            is LoginContract.LoadState.Error -> {
                when (state.type) {
                    LoginContract.Error.NULL_ERROR ->
                        binding.stateTextView.text = getString(R.string.login_state_error_null)
                    LoginContract.Error.NOT_EXIST_ERROR ->
                        binding.stateTextView.text = getString(R.string.login_state_error_not_exist)
                    LoginContract.Error.EXIST_ERROR ->
                        binding.stateTextView.text = getString(R.string.login_state_error_exist)
                }
            }
        }
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}