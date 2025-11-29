package com.tes.domain.auth

import com.tes.domain.user.UserRepository
import com.tes.domain.user.User
import org.mindrot.jbcrypt.BCrypt

/**
 * Provides authentication-related business logic for the application.
 */
class AuthService(
    private val userRepository: UserRepository,
) {

    /**
     * Validates the data of a user registration request.
     */
    fun validateRegistration(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            throw ValidationException("All fields must be non-empty.")
        }

        if (!isValidEmail(email)) {
            throw ValidationException("Invalid email format.")
        }

        if (password.length < 8) {
            throw ValidationException("Password must be at least 8 characters long.")
        }
    }

    /**
     * Checks whether the given email address is already in use.
     */
    fun checkEmailAvailability(email: String) {
        val existing = userRepository.findByEmail(email)
        if (existing != null) {
            throw EmailAlreadyExistsException("Email is already registered.")
        }
    }

    /**
     * Authenticates a user with email and password.
     */
    fun authenticate(email: String, password: String): User {
        val user = userRepository.findByEmail(email)
            ?: throw AuthenticationException("Email or password is incorrect.")

        // Verify password using BCrypt (compares plaintext against the stored hash).
        if (!BCrypt.checkpw(password, user.passwordHash)) {
            throw AuthenticationException("Email or password is incorrect.")
        }

        return user
    }

    /**
     * Hashes a plaintext password using BCrypt.
     */
    fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    /**
     * Performs a very simple email format check.
     */
    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".") && email.length >= 5
    }
}

/**
 * Thrown when validation of user input fails.
 */
class ValidationException(message: String) : Exception(message)

/**
 * Thrown when an email address is already associated with an existing user (during registration).
 */
class EmailAlreadyExistsException(message: String) : Exception(message)

/**
 * Thrown when authentication of a user fails.
 */
class AuthenticationException(message: String) : Exception(message)

