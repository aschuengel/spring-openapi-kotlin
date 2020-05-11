package com.heidelberg.kotlin.repository

import com.heidelberg.kotlin.model.User
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepository(private val jdbcTemplate: JdbcTemplate) {
    val userMapper: RowMapper<User> = RowMapper { rs, _ ->
        User(
                firstName = rs.getString("firstName"),
                lastName = rs.getString("lastName"),
                id = rs.getString("id"))
    }

    fun find(id: String): User? {
        return try {
            jdbcTemplate.queryForObject("SELECT * FROM user WHERE id = ?", userMapper, id)
        } catch (ex: EmptyResultDataAccessException) {
            null
        }
    }

    fun findAll() = jdbcTemplate.query("SELECT * FROM user", userMapper)

    fun count() = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Long::class.java)!!

    fun createUser(user: User): String {
        val id = UUID.randomUUID().toString()
        jdbcTemplate.update("INSERT INTO user (firstName, lastName, id) VALUES (?, ?, ?)",
                user.firstName, user.lastName, id)
        return id
    }

    fun deleteUser(id: String) {
        jdbcTemplate.update("DELETE FROM user WHERE id = ?", id)
    }
}
