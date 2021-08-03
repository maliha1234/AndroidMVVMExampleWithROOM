package com.example.demoLogin.ui.activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.demoLogin.ui.view.AssociatesLoginFormState;
import com.example.demoLogin.viewmodel.AssociatesLoginViewModel;
import com.example.demoLogin.viewmodel.LoginViewModelFactory;
import com.example.myapplicationlogintest.R;
import com.example.demoLogin.data.model.LoginUser;

import com.example.myapplicationlogintest.databinding.ActivityLoginBinding;



public class LoginActivity extends AppCompatActivity {

    private AssociatesLoginViewModel associatesLoginViewModel;
    private ActivityLoginBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        associatesLoginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(AssociatesLoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button showButton = binding.show;
        final ProgressBar loadingProgressBar = binding.loading;

        associatesLoginViewModel.getLoginFormState().observe(this, new Observer<AssociatesLoginFormState>() {
            @Override
            public void onChanged(@Nullable AssociatesLoginFormState associatesLoginFormState) {
                if (associatesLoginFormState == null) {
                    return;
                }
                loginButton.setEnabled(associatesLoginFormState.isDataValid());
                if (associatesLoginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(associatesLoginFormState.getUsernameError()));
                }
                if (associatesLoginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(associatesLoginFormState.getPasswordError()));
                }
            }
        });

        associatesLoginViewModel.getLoginResult().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(@Nullable LoginUser loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);


                    updateUiWithUser(loginResult.getName());



                //Complete and destroy login activity once successful
                //finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                associatesLoginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    associatesLoginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                associatesLoginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void updateUiWithUser(String model) {
        String welcome = getString(R.string.welcome) + model;
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}