package com.example.demoLogin.ui.view

/**
 * Data validation state of the login form.
 */
class AssociatesLoginFormState {
    var usernameError: Int?
        private set
    var passwordError: Int?
        private set
    var isDataValid: Boolean
        private set

    constructor(usernameError: Int?, passwordError: Int?) {
        this.usernameError = usernameError
        this.passwordError = passwordError
        isDataValid = false
    }

    constructor(isDataValid: Boolean) {
        usernameError = null
        passwordError = null
        this.isDataValid = isDataValid
    }
}