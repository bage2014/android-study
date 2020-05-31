package com.bage.tutorials.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bage.tutorials.MainActivity;
import com.bage.tutorials.R;
import com.bage.tutorials.component.DialogHelper;
import com.bage.tutorials.component.activity.ActivityHelper;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.http.server.RestResponseCodeEnum;
import com.bage.tutorials.repository.UserRepository;
import com.bage.tutorials.ui.UnlockActivity;
import com.bage.tutorials.ui.register.RegisterActivity;
import com.bage.tutorials.ui.settting.SettingsActivity;
import com.bage.tutorials.utils.AppConfigUtils;
import com.bage.tutorials.utils.JwtUtils;
import com.bage.tutorials.utils.StringUtils;

import io.jsonwebtoken.Claims;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private UserRepository userRepository;
    private DialogHelper dialogHelper;

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

        //Complete and destroy login activity once successful
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
                if (httpResult.isOk()) { // 登录成功
                    String jwt = httpResult.getData();
                    cacheUserToken(jwt);
                    gotoMain();
                } else {
                    switch (RestResponseCodeEnum.of(httpResult.getCode())){
                        case USER_LOGIN_ACCOUNT_LOCKED:
                            gotoUnlock();
                            break;
                        case UNKNOWN_EXCEPTION:
                            dialogHelper.showBasicErrorDialog(httpResult.getMsg());
                            break;
                        default:
                            showLoginFailed(httpResult.getMsg());
                            break;
                    }
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
        String jwt = userRepository.getJwt();
        if (StringUtils.isNotNullAndNotEmpty(jwt)) {
            gotoMain();
        }

        dialogHelper = new DialogHelper(this);
    }

    public void gotoUnlock() {
        ActivityHelper.startActivity(LoginActivity.this, UnlockActivity.class);
    }
    public void gotoForgetPassword(View v) {
        showLoginFailed("todo ");
    }

    public void gotoRegister(View v) {
        ActivityHelper.startActivity(LoginActivity.this, RegisterActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.login_menu_settings:
                Intent i = new Intent(LoginActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;
        }
        return false;
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
        userRepository.cacheUserJwt(jwt);
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
