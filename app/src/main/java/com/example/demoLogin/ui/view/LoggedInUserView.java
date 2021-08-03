package com.example.demoLogin.ui.view;

import androidx.annotation.Nullable;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }

    /**
     * Authentication result : success (user details) or error message.
     */
    static class AssociatesLoginResult {
        @Nullable
        private LoggedInUserView success;
        @Nullable
        private Integer error;

        AssociatesLoginResult(@Nullable Integer error) {
            this.error = error;
        }

        AssociatesLoginResult(@Nullable LoggedInUserView success) {
            this.success = success;
        }

        @Nullable
        LoggedInUserView getSuccess() {
            return success;
        }

        @Nullable
        Integer getError() {
            return error;
        }
    }
}