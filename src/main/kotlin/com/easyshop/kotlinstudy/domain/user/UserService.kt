package com.easyshop.kotlinstudy.domain.user

import com.easyshop.kotlinstudy.api.user.request.UserRequest
import com.easyshop.kotlinstudy.api.user.response.UserResponse
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Service


@Service
class UserService(private val userRepository: UserRepository) {

    @QueryMapping
    fun getUsers(): List<User> {
        return userRepository.findAll();
    }


    @QueryMapping
    fun getUser(id: Int): UserResponse {
        return userRepository.findById(id.toLong()).map { it ->
            UserResponse(it.getId().toInt(),it.getName(),it.getEmail())}
            .orElse(UserResponse(0, "Unknown", "unknown@example.com"));
    }

    @MutationMapping
    fun saveUser(user : UserRequest): Boolean {
        val userEntity = User(user.name, user.email)
        try{
            userRepository.save(userEntity);
            return true;
        }catch(e : Exception){
            return false;
        }
    }


}