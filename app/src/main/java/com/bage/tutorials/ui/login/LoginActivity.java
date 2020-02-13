package com.bage.tutorials.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bage.tutorials.MainActivity;
import com.bage.tutorials.R;
import com.bage.tutorials.domain.User;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.repository.UserRepository;
import com.bage.tutorials.ui.settting.SettingsActivity;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.JwtUtils;

import java.util.Objects;

import io.jsonwebtoken.Claims;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private UserRepository userRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.login_account);
        final EditText passwordEditText = findViewById(R.id.login_password);
        final Button loginButton = findViewById(R.id.login_btn);
        final ProgressBar loadingProgressBar = findViewById(R.id.login_loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<HttpResult>() {
            @Override
            public void onChanged(@Nullable HttpResult httpResult) {
                if (httpResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (httpResult.getCode() == 200) {
                    String jwt = httpResult.getData();
                    cacheUserToken(jwt);

                    gotoMain();
                } else {
                    showLoginFailed(httpResult.getMsg());
                }
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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        userRepository = new UserRepository(this);
        String jwt = userRepository.getLoginedUser();
        if(Objects.nonNull(jwt) && jwt.length() > 0){
            gotoMain();
        }
    }

    private void gotoMain() {
        //Complete and destroy login activity once successful
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish(); // 关闭当前
    }

    private void cacheUserToken(String jwt) {
        Claims claims = JwtUtils.decodeTokenClaims(jwt);
        Toast.makeText(getApplicationContext(), claims.getSubject(), Toast.LENGTH_LONG).show();
        userRepository.cacheUserToken(jwt);
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
