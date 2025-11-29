package com.tes.data.user

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

/**
 * Ktorm table definition for the "users" table.
 */
object UsersTable : Table<Nothing>("users") {
    val id = int("id").primaryKey()
    val firstName = varchar("first_name")
    val lastName = varchar("last_name")
    val email = varchar("email")
    val passwordHash = varchar("password_hash")
}
