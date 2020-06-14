package com.bage.tutorials.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bage.tutorials.R;
import com.bage.tutorials.component.dialog.AlertDialogHelper;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.http.server.RestResponseCodeEnum;
import com.bage.tutorials.repository.UserRepository;
import com.bage.tutorials.ui.login.LoginActivity;
import com.bage.tutorials.utils.AndroidUtils;
import com.bage.tutorials.utils.PicassoUtils;
import com.bage.tutorials.utils.StringUtils;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;
    private UserRepository userRepository;
    private AlertDialogHelper alertDialogHelper;
    private ImageView validateImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerViewModel = ViewModelProviders.of(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final EditText usernameEditText = findViewById(R.id.register_account);
        final EditText passwordEditText = findViewById(R.id.register_password);
        final EditText rePasswordEditText = findViewById(R.id.register_repassword);
        final EditText validateCodeEditText = findViewById(R.id.register_validate_code);
        final Button registerButton = findViewById(R.id.register_btn);
        final ProgressBar loadingProgressBar = findViewById(R.id.register_loading);
        validateImageView = findViewById(R.id.register_validate_image);

        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                registerButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
                if (loginFormState.getRePasswordError() != null) {
                    rePasswordEditText.setError(getString(loginFormState.getRePasswordError()));
                }
                if (loginFormState.getValidateCodeError() != null) {
                    validateCodeEditText.setError(getString(loginFormState.getValidateCodeError()));
                }
            }
        });

        registerViewModel.getRegisterResult().observe(this, new Observer<HttpResult>() {
            @Override
            public void onChanged(@Nullable HttpResult httpResult) {
                if (httpResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (httpResult.isOk()) { // 登录成功
                    gotoLogin();
                } else {
                    switch (RestResponseCodeEnum.of(httpResult.getCode())) {
                        case UNKNOWN_EXCEPTION:
                            alertDialogHelper.showBasicErrorDialog(httpResult.getMsg());
                            break;
                        default:
                            showRegisterFailed(httpResult.getMsg());
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
                registerViewModel.registerDataChanged(usernameEditText.getText().toString(),passwordEditText.getText().toString(),rePasswordEditText.getText().toString(),
                        validateCodeEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        rePasswordEditText.addTextChangedListener(afterTextChangedListener);
        validateCodeEditText.addTextChangedListener(afterTextChangedListener);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                registerViewModel.register(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),AndroidUtils.getDeviceId(),validateCodeEditText.getText().toString());
            }
        });

        userRepository = new UserRepository(this);
        String jwt = userRepository.getJwt();
        if (StringUtils.isNotNullAndNotEmpty(jwt)) {
            gotoLogin();
        }

        alertDialogHelper = new AlertDialogHelper(this);

        refreshValidateImage();
        validateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshValidateImage();
            }
        });
    }


    public void refreshValidateImage() {
        String url = "/auth-server/validate/code/generate?clientId=" + AndroidUtils.getDeviceId() + "&timestramp=" + System.currentTimeMillis();
        PicassoUtils.loadImage(this, url, validateImageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void back(View v) {
        finish();
    }

    private void gotoLogin() {
        //Complete and destroy login activity once successful
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        finish(); // 关闭当前
    }

    private void showRegisterFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
