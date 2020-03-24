package com.bage.tutorials.ui.register;

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

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<HttpResult> registerResult = new MutableLiveData<>();

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<HttpResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String username, String password, String clientId, String validateCode) {
        User user = new User();
        user.setMail(username);
        user.setPassword(password);
        String userJson = JsonUtils.toJson(user);
        List<HttpParam> params = new ArrayList<>();
        params.add(new HttpParam("clientId", clientId));
        params.add(new HttpParam("validateCodes", validateCode));
        params.add(new HttpParam("user", userJson));
        HttpRequests.post(AppConstant.urlUserRegister, params, new HttpCallback() {
            @Override
            public void onFailure(HttpResult result) {
                System.out.println("onFailure result = " + result);
                registerResult.postValue(result);
            }

            @Override
            public void onSuccess(HttpResult result) {
                System.out.println("onSuccess result = " + result);
                registerResult.postValue(result);
            }
        });
    }

    public void registerDataChanged(String username, String password, String rePassword, String validateCode) {
        if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username, null, null, null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password, null, null));
        } else if (!isPasswordValid(rePassword)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_re_password, null));
        } else if (!rePassword.equalsIgnoreCase(password)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_re_password_password, null));
        } else if (!isValidateCodeValid(validateCode)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, R.string.invalid_validate_code));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
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

    private boolean isValidateCodeValid(String validateCode) {
        if (validateCode == null) {
            return false;
        }
        return !validateCode.trim().isEmpty();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
