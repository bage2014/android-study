package com.bage.tutorials.data;

import com.bage.tutorials.data.model.LoggedInUser;
import com.bage.tutorials.http.HttpCallback;
import com.bage.tutorials.http.HttpRequests;
import com.bage.tutorials.http.HttpResult;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        HttpRequests.get("http://101.132.119.250:8866/pass/opts/appInfo/query/page", new HttpCallback() {
            @Override
            public void onFailure(HttpResult result) {
                System.out.println(result);
            }

            @Override
            public void onSuccess(HttpResult result) {
                System.out.println(result);
            }
        });

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
