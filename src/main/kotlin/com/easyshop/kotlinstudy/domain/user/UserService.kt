package com.easyshop.kotlinstudy.domain.user

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