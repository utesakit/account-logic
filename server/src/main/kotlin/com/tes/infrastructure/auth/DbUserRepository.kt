package com.tes.data.user

import com.tes.domain.user.User
import com.tes.domain.user.UserRepository
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where

/**
 * PostgreSQL / Ktorm implementation of [UserRepository].
 */
class DbUserRepository(
    private val database: Database
) : UserRepository {

    /**
     * Inserts a new user into the "users" table and returns the created domain object.
     */
    override fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): User {
        // Insert a new row into the "users" table.
        database.insert(UsersTable) {
            set(UsersTable.firstName, firstName)
            set(UsersTable.lastName, lastName)
            set(UsersTable.email, email)
            set(UsersTable.password, password)
        }

        // Load the inserted user by email so we can return it with its generated ID.
        return database
            .from(UsersTable)
            .select()
            .where { UsersTable.email eq email }
            .map { UserMapper.fromRow(it) }
            .firstOrNull()
            ?: throw IllegalStateException("User could not be loaded after insert.")
    }

    /**
     * Retrieves a user by their email address if one exists.
     */
    override fun findByEmail(email: String): User? {
        return database
            .from(UsersTable)
            .select()
            .where { UsersTable.email eq email }
            .map { UserMapper.fromRow(it) }
            .firstOrNull()
    }

    /**
     * Retrieves a user by their unique ID if one exists.
     */
    override fun findById(id: Int): User? {
        return database
            .from(UsersTable)
            .select()
            .where { UsersTable.id eq id }
            .map { UserMapper.fromRow(it) }
            .firstOrNull()
    }
}

