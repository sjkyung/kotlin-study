package com.easyshop.kotlinstudy.controller

import com.easyshop.kotlinstudy.controller.request.BoardRequest
import com.easyshop.kotlinstudy.controller.response.BoardFindResponse
import com.easyshop.kotlinstudy.domain.board.Board
import com.easyshop.kotlinstudy.domain.board.BoardService
import com.easyshop.kotlinstudy.domain.user.User
import com.easyshop.kotlinstudy.domain.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class TestController(private val testService: UserService,
                     private val boardService: BoardService) {



    @GetMapping("/hello")
    fun testApi(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(testService.getUsers());
    }


    @PostMapping("hello")
    fun testPostApi(@RequestBody user: User){
        testService.saveUser(user)
    }


    @GetMapping("/boards")
    fun testGetBoards(): ResponseEntity<List<BoardFindResponse>> {
        return ResponseEntity.ok(boardService.findAll())
    }

    @PostMapping("/boards")
    fun testPostBoards(@RequestBody boardRequest: BoardRequest) {
        return boardService.saveBoard(boardRequest)
    }

    @PutMapping("/boards/{id}")
    fun testPutBoard(@PathVariable id: Int, @RequestBody boardRequest: BoardRequest) {
        return boardService.updateBoard(id, boardRequest)
    }

    @DeleteMapping("/boards/{id}")
    fun testDeleteBoard(@PathVariable id: Int) {
        return boardService.deleteBoard(id)
    }



}