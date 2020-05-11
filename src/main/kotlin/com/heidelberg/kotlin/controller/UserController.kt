package com.heidelberg.kotlin.controller

import com.heidelberg.kotlin.model.User
import com.heidelberg.kotlin.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(val userRepository: UserRepository) {
    @GetMapping("/users/{id}")
    fun user(@PathVariable("id") id: String): ResponseEntity<User> {
        val user = userRepository.find(id)
        return if (user == null) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(user, HttpStatus.OK)
        }
    }

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable("id") id: String) = userRepository.deleteUser(id)

    @PostMapping("/users")
    fun createUser(@RequestBody user: User) = userRepository.createUser(user)

    @GetMapping("/users")
    fun users() = userRepository.findAll()

    @GetMapping("/users/count")
    fun count() = userRepository.count()
}