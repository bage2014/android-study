package com.bage.tutorials.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bage.tutorials.R;
import com.bage.tutorials.constant.AppConstant;
import com.bage.tutorials.domain.User;
import com.bage.tutorials.http.HttpCallback;
import com.bage.tutorials.http.HttpParam;
import com.bage.tutorials.http.HttpRequests;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<HttpResult> loginResult = new MutableLiveData<>();

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<HttpResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        List<HttpParam> params = new ArrayList<>();
        params.add(new HttpParam("account", username));
        params.add(new HttpParam("password", password));
        HttpRequests.get(AppConstant.urlUserLogin, params, new HttpCallback() {
            @Override
            public void onFailure(HttpResult result) {
                System.out.println("onFailure result = " + result);
                loginResult.postValue(result);
            }

            @Override
            public void onSuccess(HttpResult result) {
                System.out.println("onSuccess result = " + result);
                loginResult.postValue(result);
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
