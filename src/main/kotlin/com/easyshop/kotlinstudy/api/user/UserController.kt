package com.easyshop.kotlinstudy.api.user

import com.easyshop.kotlinstudy.domain.user.User
import com.easyshop.kotlinstudy.domain.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(private val testService: UserService) {


    @GetMapping("/Users")
    fun testApi(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(testService.getUsers());
    }


    @PostMapping("/Users")
    fun testPostApi(@RequestBody user: User) {
        testService.saveUser(user)
    }

}