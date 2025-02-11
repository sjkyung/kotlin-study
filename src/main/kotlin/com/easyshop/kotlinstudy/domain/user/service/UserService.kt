package com.easyshop.kotlinstudy.domain.user.service

import com.easyshop.kotlinstudy.domain.user.User
import com.easyshop.kotlinstudy.domain.user.repository.UserRepository
import org.springframework.stereotype.Service


@Service
class UserService(private val userRepository: UserRepository) {

    fun getUsers(): List<User> {
        return userRepository.findAll();
    }


    fun saveUser(user : User) {
        userRepository.save(user);
    }


}