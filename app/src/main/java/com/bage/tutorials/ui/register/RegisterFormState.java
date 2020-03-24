package com.bage.tutorials.ui.register;

import androidx.annotation.Nullable;

/**
 * Data validation state of the register form.
 */
class RegisterFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer rePasswordError;
    @Nullable
    private Integer validateCodeError;

    private boolean isDataValid;

    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError,@Nullable Integer rePasswordError, @Nullable Integer validateCodeError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.rePasswordError = rePasswordError;
        this.validateCodeError = validateCodeError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.rePasswordError = null;
        this.validateCodeError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getRePasswordError() {
        return rePasswordError;
    }

    @Nullable
    Integer getValidateCodeError() {
        return validateCodeError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
