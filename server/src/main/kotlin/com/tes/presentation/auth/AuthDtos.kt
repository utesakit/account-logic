package com.tes.api.auth

import com.tes.domain.user.User
import kotlinx.serialization.Serializable

/**
 * Data Transfer Objects (DTOs) used by the authentication REST API.
 */

/**
 * Request body for user registration.
 */
@Serializable
data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

/**
 * Request body for logging in an existing user.
 */
@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

/**
 * API response representing a single user.
 */
@Serializable
data class UserResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String
)

/**
 * Generic API response with a readable message.
 */
@Serializable
data class MessageResponse(
    val message: String
)

/**
 * API response returned after successful login or registration.
 */
@Serializable
data class AuthResponse(
    val user: UserResponse
)

/**
 * Maps a domain [User] entity to a [UserResponse] DTO.
 */
fun User.toResponse(): UserResponse =
    UserResponse(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email
    )

