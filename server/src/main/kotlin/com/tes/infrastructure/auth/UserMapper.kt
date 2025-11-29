package com.tes.data.user

import com.tes.domain.user.User
import org.ktorm.dsl.QueryRowSet

/**
 * Helper for converting database rows into [User] domain objects.
 */
object UserMapper {

    /**
     * Converts a database result row into a [User] domain object.
     */
    fun fromRow(row: QueryRowSet): User {
        return User(
            id = row[UsersTable.id]!!,
            firstName = row[UsersTable.firstName]!!,
            lastName = row[UsersTable.lastName]!!,
            email = row[UsersTable.email]!!,
            passwordHash = row[UsersTable.passwordHash]!!
        )
    }
}
