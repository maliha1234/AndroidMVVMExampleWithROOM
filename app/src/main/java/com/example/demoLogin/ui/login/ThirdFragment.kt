package com.example.demoLogin.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationlogintest.databinding.ThirdFragmentBinding
import com.example.myapplicationlogintest.R


class ThirdFragment : Fragment() {

    companion object {
        fun newInstance() = ThirdFragment()
    }

    private lateinit var loginViewModel: LoginViewModel

    private var _binding: ThirdFragmentBinding? = null

    // with the backing property of the kotlin
    // we extract
    // the non null value of the _binding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ThirdFragmentBinding.inflate(inflater, container, false)
        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        val showButton = binding.show
        val loadingProgressBar = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)
        loginViewModel.getLoginFormState().observe(viewLifecycleOwner,
            Observer<LoginFormState?> { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                if (loginFormState.usernameError != null) {
                    usernameEditText.error = getString(loginFormState.usernameError!!)
                }
                if (loginFormState.passwordError != null) {
                    passwordEditText.error = getString(loginFormState.passwordError!!)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                if (loginResult == null) {
                    return@Observer
                }
                loadingProgressBar.visibility = View.GONE
                updateUiWithUser(loginResult.name)

                //Complete and destroy login activity once successful
                //finish();
            })


        val afterTextChangedListener: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }

        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel.login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUiWithUser(model: String) {
        val welcome = getString(R.string.welcome) + model
        // TODO : initiate successful logged in experience
        Toast.makeText(activity, welcome, Toast.LENGTH_LONG).show()
    }

}